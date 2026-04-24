package com.communityservice.servlet;

import com.communityservice.dao.FeedbackDAO;
import com.communityservice.dao.RequestDAO;
import com.communityservice.dao.UserDAO;
import com.communityservice.dao.VolunteerDAO;
import com.communityservice.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final RequestDAO requestDAO = new RequestDAO();
    private final VolunteerDAO volunteerDAO = new VolunteerDAO();
    private final UserDAO userDAO = new UserDAO();
    private final FeedbackDAO feedbackDAO = new FeedbackDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("currentUser");
        request.setAttribute("activeRequests", requestDAO.getActiveRequests());
        request.setAttribute("myRequests", requestDAO.getRequestsByUser(currentUser.getId()));
        request.setAttribute("assignedTasks", volunteerDAO.getAssignedTasks(currentUser.getId()));
        request.setAttribute("allUsers", userDAO.getAllUsers());
        request.setAttribute("completedRequests", requestDAO.getCompletedRequests());
        request.setAttribute("feedbackList", feedbackDAO.getAllFeedback());
        request.setAttribute("activeRequestCount", requestDAO.countActiveRequests());
        request.setAttribute("completedRequestCount", requestDAO.countCompletedRequests());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp");
        dispatcher.forward(request, response);
    }
}
