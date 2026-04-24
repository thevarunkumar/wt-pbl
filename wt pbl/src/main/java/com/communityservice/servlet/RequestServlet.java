package com.communityservice.servlet;

import com.communityservice.dao.RequestDAO;
import com.communityservice.dao.VolunteerDAO;
import com.communityservice.model.ServiceRequest;
import com.communityservice.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/request")
public class RequestServlet extends HttpServlet {
    private final RequestDAO requestDAO = new RequestDAO();
    private final VolunteerDAO volunteerDAO = new VolunteerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("currentUser");
        request.setAttribute("activeRequests", requestDAO.getActiveRequests());
        request.setAttribute("myRequests", requestDAO.getRequestsByUser(currentUser.getId()));
        request.setAttribute("assignedTasks", volunteerDAO.getAssignedTasks(currentUser.getId()));

        if ("new".equalsIgnoreCase(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/post-request.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("allRequests", requestDAO.getAllRequests());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/view-requests.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (!"create".equalsIgnoreCase(action)) {
            response.sendRedirect(request.getContextPath() + "/request?action=list");
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("currentUser");
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setUserId(currentUser.getId());
        serviceRequest.setTitle(request.getParameter("title"));
        serviceRequest.setDescription(request.getParameter("description"));
        serviceRequest.setLocation(request.getParameter("location"));
        serviceRequest.setContact(request.getParameter("contact"));
        serviceRequest.setCategory(request.getParameter("category"));
        serviceRequest.setStatus("PENDING");

        if (isBlank(serviceRequest.getTitle()) || isBlank(serviceRequest.getDescription()) || isBlank(serviceRequest.getLocation())
                || isBlank(serviceRequest.getContact()) || isBlank(serviceRequest.getCategory())) {
            session.setAttribute("flashError", "All request fields are required.");
            response.sendRedirect(request.getContextPath() + "/request?action=new");
            return;
        }

        if (requestDAO.createRequest(serviceRequest)) {
            session.setAttribute("flashMessage", "Service request posted successfully.");
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            session.setAttribute("flashError", "Could not save the request.");
            response.sendRedirect(request.getContextPath() + "/request?action=new");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
