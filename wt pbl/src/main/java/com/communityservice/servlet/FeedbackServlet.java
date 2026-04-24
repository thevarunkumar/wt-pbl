package com.communityservice.servlet;

import com.communityservice.dao.FeedbackDAO;
import com.communityservice.dao.VolunteerDAO;
import com.communityservice.model.Feedback;
import com.communityservice.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {
    private final FeedbackDAO feedbackDAO = new FeedbackDAO();
    private final VolunteerDAO volunteerDAO = new VolunteerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("currentUser");
        int requestId = parseInt(request.getParameter("requestId"));
        int rating = parseInt(request.getParameter("rating"));
        String comments = request.getParameter("comments");
        Integer volunteerId = volunteerDAO.getVolunteerIdByRequestId(requestId);

        if (volunteerId == null || requestId <= 0 || rating < 1 || rating > 5) {
            session.setAttribute("flashError", "Please choose a valid completed request and rating.");
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        Feedback feedback = new Feedback();
        feedback.setUserId(currentUser.getId());
        feedback.setVolunteerId(volunteerId);
        feedback.setRequestId(requestId);
        feedback.setRating(rating);
        feedback.setComments(comments == null ? "" : comments.trim());

        if (feedbackDAO.saveFeedback(feedback)) {
            session.setAttribute("flashMessage", "Thank you for your feedback.");
        } else {
            session.setAttribute("flashError", "Could not save feedback.");
        }

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }
}
