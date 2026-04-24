package com.communityservice.servlet;

import com.communityservice.dao.FeedbackDAO;
import com.communityservice.dao.RequestDAO;
import com.communityservice.dao.UserDAO;
import com.communityservice.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();
    private final RequestDAO requestDAO = new RequestDAO();
    private final FeedbackDAO feedbackDAO = new FeedbackDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("currentUser");
        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        request.setAttribute("allUsers", userDAO.getAllUsers());
        request.setAttribute("allRequests", requestDAO.getAllRequests());
        request.setAttribute("completedRequests", requestDAO.getCompletedRequests());
        request.setAttribute("feedbackList", feedbackDAO.getAllFeedback());
        request.setAttribute("activeRequestCount", requestDAO.countActiveRequests());
        request.setAttribute("completedRequestCount", requestDAO.countCompletedRequests());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin-dashboard.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("currentUser");
        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        String action = request.getParameter("action");
        if ("deleteUser".equalsIgnoreCase(action)) {
            int userId = parseInt(request.getParameter("userId"));
            if (userDAO.deleteUser(userId)) {
                session.setAttribute("flashMessage", "User deleted successfully.");
            } else {
                session.setAttribute("flashError", "Could not delete user.");
            }
        } else if ("markCompleted".equalsIgnoreCase(action)) {
            int requestId = parseInt(request.getParameter("requestId"));
            if (requestDAO.updateStatus(requestId, "COMPLETED")) {
                session.setAttribute("flashMessage", "Request marked as completed.");
            } else {
                session.setAttribute("flashError", "Could not update request status.");
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin");
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }
}
