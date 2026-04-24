package com.communityservice.dao;

import com.communityservice.model.Feedback;
import com.communityservice.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    public boolean saveFeedback(Feedback feedback) {
        String sql = "INSERT INTO feedback (user_id, volunteer_id, request_id, rating, comments) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, feedback.getUserId());
            statement.setInt(2, feedback.getVolunteerId());
            statement.setInt(3, feedback.getRequestId());
            statement.setInt(4, feedback.getRating());
            statement.setString(5, feedback.getComments());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM feedback ORDER BY id DESC";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                feedbackList.add(mapFeedback(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    public List<Feedback> getFeedbackByVolunteer(int volunteerId) {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT * FROM feedback WHERE volunteer_id = ? ORDER BY id DESC";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, volunteerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    feedbackList.add(mapFeedback(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    private Feedback mapFeedback(ResultSet resultSet) throws Exception {
        Feedback feedback = new Feedback();
        feedback.setId(resultSet.getInt("id"));
        feedback.setUserId(resultSet.getInt("user_id"));
        feedback.setVolunteerId(resultSet.getInt("volunteer_id"));
        feedback.setRequestId(resultSet.getInt("request_id"));
        feedback.setRating(resultSet.getInt("rating"));
        feedback.setComments(resultSet.getString("comments"));
        feedback.setCreatedAt(resultSet.getString("created_at"));
        return feedback;
    }
}
