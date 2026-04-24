package com.communityservice.dao;

import com.communityservice.model.VolunteerAssignment;
import com.communityservice.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VolunteerDAO {

    public boolean assignVolunteer(int volunteerUserId, int requestId) {
        if (isAlreadyAssigned(volunteerUserId, requestId)) {
            return false;
        }

        String sql = "INSERT INTO volunteers (user_id, request_id) VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, volunteerUserId);
            statement.setInt(2, requestId);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAlreadyAssigned(int volunteerUserId, int requestId) {
        String sql = "SELECT id FROM volunteers WHERE user_id = ? AND request_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, volunteerUserId);
            statement.setInt(2, requestId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<VolunteerAssignment> getAssignedTasks(int volunteerUserId) {
        List<VolunteerAssignment> assignments = new ArrayList<>();
        String sql = "SELECT v.id, v.user_id, v.request_id, r.title, r.status, r.location, u.name AS volunteer_name "
                + "FROM volunteers v "
                + "INNER JOIN requests r ON v.request_id = r.id "
                + "INNER JOIN users u ON v.user_id = u.id "
                + "WHERE v.user_id = ? ORDER BY v.id DESC";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, volunteerUserId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    VolunteerAssignment assignment = new VolunteerAssignment();
                    assignment.setId(resultSet.getInt("id"));
                    assignment.setUserId(resultSet.getInt("user_id"));
                    assignment.setRequestId(resultSet.getInt("request_id"));
                    assignment.setVolunteerName(resultSet.getString("volunteer_name"));
                    assignment.setRequestTitle(resultSet.getString("title"));
                    assignment.setRequestStatus(resultSet.getString("status"));
                    assignment.setLocation(resultSet.getString("location"));
                    assignments.add(assignment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assignments;
    }

    public Integer getVolunteerIdByRequestId(int requestId) {
        String sql = "SELECT user_id FROM volunteers WHERE request_id = ? ORDER BY id DESC LIMIT 1";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, requestId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("user_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
