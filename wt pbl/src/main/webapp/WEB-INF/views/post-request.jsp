<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Post Request - Community Service System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script defer src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<%
    String flashMessage = (String) session.getAttribute("flashMessage");
    String flashError = (String) session.getAttribute("flashError");
    if (flashMessage != null) {
        session.removeAttribute("flashMessage");
    }
    if (flashError != null) {
        session.removeAttribute("flashError");
    }
%>
<div class="form-page">
    <div class="panel form-panel">
        <div class="section-head">
            <h2>Post a Service Request</h2>
            <a href="${pageContext.request.contextPath}/dashboard">Back to dashboard</a>
        </div>
        <% if (flashMessage != null) { %>
        <div class="alert success"><%= flashMessage %></div>
        <% } %>
        <% if (flashError != null) { %>
        <div class="alert danger"><%= flashError %></div>
        <% } %>
        <form class="auth-form" action="${pageContext.request.contextPath}/request" method="post" onsubmit="return validateRequestForm(this)">
            <input type="hidden" name="action" value="create">
            <label>Title</label>
            <input type="text" name="title" placeholder="Request title" required>
            <label>Description</label>
            <textarea name="description" rows="4" placeholder="Describe the help you need" required></textarea>
            <label>Category</label>
            <select name="category" required>
                <option value="Blood Donation">Blood Donation</option>
                <option value="Food Help">Food Help</option>
                <option value="Teaching">Teaching</option>
                <option value="Emergency">Emergency</option>
            </select>
            <label>Location</label>
            <input type="text" name="location" placeholder="Location" required>
            <label>Contact</label>
            <input type="text" name="contact" placeholder="Phone number or email" required>
            <button class="btn-primary full-width" type="submit">Submit Request</button>
        </form>
    </div>
</div>
</body>
</html>
