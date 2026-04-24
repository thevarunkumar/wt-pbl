package com.communityservice.dao;

import com.communityservice.model.ServiceRequest;
import com.communityservice.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {

    public boolean createRequest(ServiceRequest serviceRequest) {
        String sql = "INSERT INTO requests (user_id, title, description, status, location, contact, category) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, serviceRequest.getUserId());
            statement.setString(2, serviceRequest.getTitle());
            statement.setString(3, serviceRequest.getDescription());
            statement.setString(4, serviceRequest.getStatus());
            statement.setString(5, serviceRequest.getLocation());
            statement.setString(6, serviceRequest.getContact());
            statement.setString(7, serviceRequest.getCategory());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ServiceRequest> getAllRequests() {
        List<ServiceRequest> requests = new ArrayList<>();
        String sql = "SELECT r.*, u.name AS requester_name FROM requests r INNER JOIN users u ON r.user_id = u.id ORDER BY r.id DESC";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                requests.add(mapRequest(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public List<ServiceRequest> getActiveRequests() {
        List<ServiceRequest> requests = new ArrayList<>();
        String sql = "SELECT r.*, u.name AS requester_name FROM requests r INNER JOIN users u ON r.user_id = u.id WHERE r.status IN ('PENDING', 'ACCEPTED') ORDER BY r.id DESC";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                requests.add(mapRequest(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public List<ServiceRequest> getRequestsByUser(int userId) {
        List<ServiceRequest> requests = new ArrayList<>();
        String sql = "SELECT r.*, u.name AS requester_name FROM requests r INNER JOIN users u ON r.user_id = u.id WHERE r.user_id = ? ORDER BY r.id DESC";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    requests.add(mapRequest(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public List<ServiceRequest> getCompletedRequests() {
        List<ServiceRequest> requests = new ArrayList<>();
        String sql = "SELECT r.*, u.name AS requester_name FROM requests r INNER JOIN users u ON r.user_id = u.id WHERE r.status = 'COMPLETED' ORDER BY r.id DESC";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                requests.add(mapRequest(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public ServiceRequest getRequestById(int id) {
        String sql = "SELECT r.*, u.name AS requester_name FROM requests r INNER JOIN users u ON r.user_id = u.id WHERE r.id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRequest(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateStatus(int requestId, String status) {
        String sql = "UPDATE requests SET status = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, requestId);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countActiveRequests() {
        String sql = "SELECT COUNT(*) FROM requests WHERE status IN ('PENDING', 'ACCEPTED')";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countCompletedRequests() {
        String sql = "SELECT COUNT(*) FROM requests WHERE status = 'COMPLETED'";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private ServiceRequest mapRequest(ResultSet resultSet) throws Exception {
        ServiceRequest request = new ServiceRequest();
        request.setId(resultSet.getInt("id"));
        request.setUserId(resultSet.getInt("user_id"));
        request.setRequesterName(resultSet.getString("requester_name"));
        request.setTitle(resultSet.getString("title"));
        request.setDescription(resultSet.getString("description"));
        request.setStatus(resultSet.getString("status"));
        request.setLocation(resultSet.getString("location"));
        request.setContact(resultSet.getString("contact"));
        request.setCategory(resultSet.getString("category"));
        request.setCreatedAt(resultSet.getString("created_at"));
        return request;
    }
}
