package com.communityservice.servlet;

import com.communityservice.dao.RequestDAO;
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

@WebServlet("/volunteer")
public class VolunteerServlet extends HttpServlet {
    private final RequestDAO requestDAO = new RequestDAO();
    private final VolunteerDAO volunteerDAO = new VolunteerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("currentUser");
        request.setAttribute("allRequests", requestDAO.getActiveRequests());
        request.setAttribute("assignedTasks", volunteerDAO.getAssignedTasks(currentUser.getId()));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/view-requests.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("currentUser");
        int requestId = parseInt(request.getParameter("requestId"));

        if ("accept".equalsIgnoreCase(action)) {
            boolean updated = requestDAO.updateStatus(requestId, "ACCEPTED");
            boolean assigned = volunteerDAO.assignVolunteer(currentUser.getId(), requestId);
            if (updated || assigned) {
                session.setAttribute("flashMessage", "Request accepted and assigned to you.");
            } else {
                session.setAttribute("flashError", "Could not accept this request.");
            }
        } else if ("reject".equalsIgnoreCase(action)) {
            session.setAttribute("flashMessage", "Request skipped.");
        }

        response.sendRedirect(request.getContextPath() + "/volunteer");
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }
}
