<%@ page import="java.util.List" %>
<%@ page import="com.communityservice.model.User" %>
<%@ page import="com.communityservice.model.ServiceRequest" %>
<%@ page import="com.communityservice.model.VolunteerAssignment" %>
<%@ page import="com.communityservice.model.Feedback" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Community Service System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script defer src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<%
    User currentUser = (User) session.getAttribute("currentUser");
    String flashMessage = (String) session.getAttribute("flashMessage");
    String flashError = (String) session.getAttribute("flashError");
    if (flashMessage != null) {
        session.removeAttribute("flashMessage");
    }
    if (flashError != null) {
        session.removeAttribute("flashError");
    }
    List<ServiceRequest> activeRequests = (List<ServiceRequest>) request.getAttribute("activeRequests");
    List<ServiceRequest> myRequests = (List<ServiceRequest>) request.getAttribute("myRequests");
    List<VolunteerAssignment> assignedTasks = (List<VolunteerAssignment>) request.getAttribute("assignedTasks");
    List<User> allUsers = (List<User>) request.getAttribute("allUsers");
    List<ServiceRequest> completedRequests = (List<ServiceRequest>) request.getAttribute("completedRequests");
    List<Feedback> feedbackList = (List<Feedback>) request.getAttribute("feedbackList");
    Integer activeRequestCount = (Integer) request.getAttribute("activeRequestCount");
    Integer completedRequestCount = (Integer) request.getAttribute("completedRequestCount");
    String role = currentUser != null ? currentUser.getRole() : "";
%>
<header class="topbar dashboard-topbar">
    <div class="brand">
        <span class="brand-mark">CS</span>
        <div>
            <h1>Dashboard</h1>
            <p>Welcome, <%= currentUser != null ? currentUser.getName() : "User" %></p>
        </div>
    </div>
    <nav class="nav-links">
        <a href="${pageContext.request.contextPath}/request?action=new">Post Request</a>
        <a href="${pageContext.request.contextPath}/request?action=list">View Requests</a>
        <% if ("ADMIN".equalsIgnoreCase(role)) { %>
        <a href="${pageContext.request.contextPath}/admin">Admin Panel</a>
        <% } %>
        <a href="${pageContext.request.contextPath}/logout" class="btn-outline">Logout</a>
    </nav>
</header>
<main class="dashboard-grid">
    <% if (flashMessage != null) { %>
    <div class="alert success full-span"><%= flashMessage %></div>
    <% } %>
    <% if (flashError != null) { %>
    <div class="alert danger full-span"><%= flashError %></div>
    <% } %>

    <section class="panel stats-card">
        <h3>Your Profile</h3>
        <p><strong>Name:</strong> <%= currentUser.getName() %></p>
        <p><strong>Email:</strong> <%= currentUser.getEmail() %></p>
        <p><strong>Role:</strong> <%= currentUser.getRole() %></p>
        <div class="mini-stats compact">
            <div><strong><%= activeRequestCount != null ? activeRequestCount : 0 %></strong><span>Active Requests</span></div>
            <div><strong><%= completedRequestCount != null ? completedRequestCount : 0 %></strong><span>Completed</span></div>
        </div>
    </section>

    <section class="panel wide-panel">
        <div class="section-head">
            <h3>Active Requests</h3>
            <a href="${pageContext.request.contextPath}/request?action=list">Open Request Board</a>
        </div>
        <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Category</th>
                    <th>Location</th>
                    <th>Status</th>
                    <th>Requester</th>
                </tr>
                </thead>
                <tbody>
                <% if (activeRequests != null && !activeRequests.isEmpty()) { %>
                <% for (ServiceRequest item : activeRequests) { %>
                <tr>
                    <td><%= item.getId() %></td>
                    <td><%= item.getTitle() %></td>
                    <td><%= item.getCategory() %></td>
                    <td><%= item.getLocation() %></td>
                    <td><span class="status-badge status-<%= item.getStatus().toLowerCase() %>"><%= item.getStatus() %></span></td>
                    <td><%= item.getRequesterName() %></td>
                </tr>
                <% } %>
                <% } else { %>
                <tr><td colspan="6">No active requests found.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </section>

    <section class="panel wide-panel">
        <div class="section-head">
            <h3>My Requests</h3>
            <a href="${pageContext.request.contextPath}/request?action=new">Create New</a>
        </div>
        <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Category</th>
                    <th>Location</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <% if (myRequests != null && !myRequests.isEmpty()) { %>
                <% for (ServiceRequest item : myRequests) { %>
                <tr>
                    <td><%= item.getId() %></td>
                    <td><%= item.getTitle() %></td>
                    <td><%= item.getCategory() %></td>
                    <td><%= item.getLocation() %></td>
                    <td><span class="status-badge status-<%= item.getStatus().toLowerCase() %>"><%= item.getStatus() %></span></td>
                </tr>
                <% } %>
                <% } else { %>
                <tr><td colspan="5">You have not posted any requests yet.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </section>

    <section class="panel wide-panel">
        <div class="section-head">
            <h3>Assigned Tasks</h3>
            <a href="${pageContext.request.contextPath}/volunteer">Open Volunteer View</a>
        </div>
        <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <th>Request ID</th>
                    <th>Task</th>
                    <th>Status</th>
                    <th>Location</th>
                </tr>
                </thead>
                <tbody>
                <% if (assignedTasks != null && !assignedTasks.isEmpty()) { %>
                <% for (VolunteerAssignment item : assignedTasks) { %>
                <tr>
                    <td><%= item.getRequestId() %></td>
                    <td><%= item.getRequestTitle() %></td>
                    <td><span class="status-badge status-<%= item.getRequestStatus().toLowerCase() %>"><%= item.getRequestStatus() %></span></td>
                    <td><%= item.getLocation() %></td>
                </tr>
                <% } %>
                <% } else { %>
                <tr><td colspan="4">No assigned tasks yet.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </section>

    <section class="panel wide-panel">
        <div class="section-head">
            <h3>Rate a Volunteer</h3>
            <p>Use the request ID from your completed service.</p>
        </div>
        <form class="inline-form" action="${pageContext.request.contextPath}/feedback" method="post" onsubmit="return validateFeedbackForm(this)">
            <input type="number" name="requestId" min="1" placeholder="Request ID" required>
            <input type="number" name="rating" min="1" max="5" placeholder="Rating 1-5" required>
            <input type="text" name="comments" placeholder="Comments">
            <button class="btn-primary" type="submit">Submit Feedback</button>
        </form>
    </section>

    <% if ("ADMIN".equalsIgnoreCase(role)) { %>
    <section class="panel wide-panel">
        <div class="section-head">
            <h3>Admin Snapshot</h3>
            <a href="${pageContext.request.contextPath}/admin">Open Admin Panel</a>
        </div>
        <div class="table-wrap">
            <table>
                <thead>
                <tr><th>Total Users</th><th>Completed Services</th><th>Feedback Records</th></tr>
                </thead>
                <tbody>
                <tr>
                    <td><%= allUsers != null ? allUsers.size() : 0 %></td>
                    <td><%= completedRequests != null ? completedRequests.size() : 0 %></td>
                    <td><%= feedbackList != null ? feedbackList.size() : 0 %></td>
                </tr>
                </tbody>
            </table>
        </div>
    </section>
    <% } %>
</main>
</body>
</html>
