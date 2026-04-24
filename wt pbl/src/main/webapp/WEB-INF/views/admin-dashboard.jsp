<%@ page import="java.util.List" %>
<%@ page import="com.communityservice.model.User" %>
<%@ page import="com.communityservice.model.ServiceRequest" %>
<%@ page import="com.communityservice.model.Feedback" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Community Service System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script defer src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<%
    List<User> allUsers = (List<User>) request.getAttribute("allUsers");
    List<ServiceRequest> allRequests = (List<ServiceRequest>) request.getAttribute("allRequests");
    List<ServiceRequest> completedRequests = (List<ServiceRequest>) request.getAttribute("completedRequests");
    List<Feedback> feedbackList = (List<Feedback>) request.getAttribute("feedbackList");
    Integer activeRequestCount = (Integer) request.getAttribute("activeRequestCount");
    Integer completedRequestCount = (Integer) request.getAttribute("completedRequestCount");
    String flashMessage = (String) session.getAttribute("flashMessage");
    String flashError = (String) session.getAttribute("flashError");
    if (flashMessage != null) {
        session.removeAttribute("flashMessage");
    }
    if (flashError != null) {
        session.removeAttribute("flashError");
    }
%>
<div class="form-page list-page">
    <div class="panel form-panel wide-panel">
        <div class="section-head">
            <h2>Admin Dashboard</h2>
            <a href="${pageContext.request.contextPath}/dashboard">Back to dashboard</a>
        </div>
        <% if (flashMessage != null) { %>
        <div class="alert success"><%= flashMessage %></div>
        <% } %>
        <% if (flashError != null) { %>
        <div class="alert danger"><%= flashError %></div>
        <% } %>
        <div class="mini-stats compact">
            <div><strong><%= allUsers != null ? allUsers.size() : 0 %></strong><span>Users</span></div>
            <div><strong><%= activeRequestCount != null ? activeRequestCount : 0 %></strong><span>Active Requests</span></div>
            <div><strong><%= completedRequestCount != null ? completedRequestCount : 0 %></strong><span>Completed</span></div>
            <div><strong><%= feedbackList != null ? feedbackList.size() : 0 %></strong><span>Feedback</span></div>
        </div>

        <section class="admin-block">
            <h3>Manage Users</h3>
            <div class="table-wrap">
                <table>
                    <thead>
                    <tr><th>ID</th><th>Name</th><th>Email</th><th>Role</th><th>Action</th></tr>
                    </thead>
                    <tbody>
                    <% if (allUsers != null && !allUsers.isEmpty()) { %>
                    <% for (User user : allUsers) { %>
                    <tr>
                        <td><%= user.getId() %></td>
                        <td><%= user.getName() %></td>
                        <td><%= user.getEmail() %></td>
                        <td><%= user.getRole() %></td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin" method="post" onsubmit="return confirm('Delete this user?')">
                                <input type="hidden" name="action" value="deleteUser">
                                <input type="hidden" name="userId" value="<%= user.getId() %>">
                                <button type="submit" class="btn-small btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                    <% } %>
                    <% } else { %>
                    <tr><td colspan="5">No users found.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </section>

        <section class="admin-block">
            <h3>Manage Requests</h3>
            <div class="table-wrap">
                <table>
                    <thead>
                    <tr><th>ID</th><th>Title</th><th>Category</th><th>Status</th><th>Action</th></tr>
                    </thead>
                    <tbody>
                    <% if (allRequests != null && !allRequests.isEmpty()) { %>
                    <% for (ServiceRequest item : allRequests) { %>
                    <tr>
                        <td><%= item.getId() %></td>
                        <td><%= item.getTitle() %></td>
                        <td><%= item.getCategory() %></td>
                        <td><span class="status-badge status-<%= item.getStatus().toLowerCase() %>"><%= item.getStatus() %></span></td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin" method="post">
                                <input type="hidden" name="action" value="markCompleted">
                                <input type="hidden" name="requestId" value="<%= item.getId() %>">
                                <button type="submit" class="btn-small">Mark Completed</button>
                            </form>
                        </td>
                    </tr>
                    <% } %>
                    <% } else { %>
                    <tr><td colspan="5">No requests available.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </section>

        <section class="admin-block">
            <h3>Completed Services</h3>
            <div class="table-wrap">
                <table>
                    <thead>
                    <tr><th>ID</th><th>Title</th><th>Requester</th><th>Status</th></tr>
                    </thead>
                    <tbody>
                    <% if (completedRequests != null && !completedRequests.isEmpty()) { %>
                    <% for (ServiceRequest item : completedRequests) { %>
                    <tr>
                        <td><%= item.getId() %></td>
                        <td><%= item.getTitle() %></td>
                        <td><%= item.getRequesterName() %></td>
                        <td><span class="status-badge status-completed"><%= item.getStatus() %></span></td>
                    </tr>
                    <% } %>
                    <% } else { %>
                    <tr><td colspan="4">No completed services yet.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </section>

        <section class="admin-block">
            <h3>Feedback</h3>
            <div class="table-wrap">
                <table>
                    <thead>
                    <tr><th>ID</th><th>Request ID</th><th>Volunteer ID</th><th>Rating</th><th>Comments</th></tr>
                    </thead>
                    <tbody>
                    <% if (feedbackList != null && !feedbackList.isEmpty()) { %>
                    <% for (Feedback item : feedbackList) { %>
                    <tr>
                        <td><%= item.getId() %></td>
                        <td><%= item.getRequestId() %></td>
                        <td><%= item.getVolunteerId() %></td>
                        <td><%= item.getRating() %>/5</td>
                        <td><%= item.getComments() %></td>
                    </tr>
                    <% } %>
                    <% } else { %>
                    <tr><td colspan="5">No feedback submitted yet.</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </section>
    </div>
</div>
</body>
</html>
