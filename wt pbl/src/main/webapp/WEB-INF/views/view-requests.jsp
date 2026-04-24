<%@ page import="java.util.List" %>
<%@ page import="com.communityservice.model.ServiceRequest" %>
<%@ page import="com.communityservice.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Requests - Community Service System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script defer src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<%
    User currentUser = (User) session.getAttribute("currentUser");
    List<ServiceRequest> allRequests = (List<ServiceRequest>) request.getAttribute("allRequests");
    String role = currentUser != null ? currentUser.getRole() : "";
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
            <h2>Service Requests</h2>
            <div class="section-actions">
                <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
                <% if (!"VOLUNTEER".equalsIgnoreCase(role)) { %>
                <a href="${pageContext.request.contextPath}/request?action=new">Post Request</a>
                <% } %>
            </div>
        </div>
        <% if (flashMessage != null) { %>
        <div class="alert success"><%= flashMessage %></div>
        <% } %>
        <% if (flashError != null) { %>
        <div class="alert danger"><%= flashError %></div>
        <% } %>
        <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Category</th>
                    <th>Description</th>
                    <th>Location</th>
                    <th>Contact</th>
                    <th>Status</th>
                    <th>Requester</th>
                    <% if ("VOLUNTEER".equalsIgnoreCase(role)) { %><th>Actions</th><% } %>
                </tr>
                </thead>
                <tbody>
                <% if (allRequests != null && !allRequests.isEmpty()) { %>
                <% for (ServiceRequest item : allRequests) { %>
                <tr>
                    <td><%= item.getId() %></td>
                    <td><%= item.getTitle() %></td>
                    <td><%= item.getCategory() %></td>
                    <td><%= item.getDescription() %></td>
                    <td><%= item.getLocation() %></td>
                    <td><%= item.getContact() %></td>
                    <td><span class="status-badge status-<%= item.getStatus().toLowerCase() %>"><%= item.getStatus() %></span></td>
                    <td><%= item.getRequesterName() %></td>
                    <% if ("VOLUNTEER".equalsIgnoreCase(role)) { %>
                    <td>
                        <form class="inline-buttons" action="${pageContext.request.contextPath}/volunteer" method="post">
                            <input type="hidden" name="requestId" value="<%= item.getId() %>">
                            <button type="submit" name="action" value="accept" class="btn-small">Accept</button>
                            <button type="submit" name="action" value="reject" class="btn-small btn-muted">Reject</button>
                        </form>
                    </td>
                    <% } %>
                </tr>
                <% } %>
                <% } else { %>
                <tr><td colspan="<%= "VOLUNTEER".equalsIgnoreCase(role) ? 9 : 8 %>">No requests available.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
