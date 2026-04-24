<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Community Service System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script defer src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<div class="auth-shell">
    <div class="auth-card panel">
        <a class="back-link" href="index.jsp">Back to home</a>
        <h2>Create Account</h2>
        <p>Join as a requester or volunteer and start helping the community.</p>

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
        <% if (flashMessage != null) { %>
        <div class="alert success"><%= flashMessage %></div>
        <% } %>
        <% if (flashError != null) { %>
        <div class="alert danger"><%= flashError %></div>
        <% } %>

        <form class="auth-form" action="${pageContext.request.contextPath}/auth" method="post" autocomplete="off" onsubmit="return validateRegisterForm(this)">
            <input type="hidden" name="action" value="register">
            <label>Full Name</label>
            <input type="text" name="name" placeholder="Your name" required>
            <label>Email</label>
            <input type="email" name="email" placeholder="Your email" required>
            <label>Password</label>
            <input type="password" name="password" autocomplete="new-password" placeholder="Create a password" required>
            <label>Role</label>
            <select name="role" required>
                <option value="REQUESTER">Requester</option>
                <option value="VOLUNTEER">Volunteer</option>
            </select>
            <button class="btn-primary full-width" type="submit">Register</button>
        </form>
        <p class="auth-footer">Already have an account? <a href="login.jsp">Login here</a></p>
    </div>
</div>
</body>
</html>
