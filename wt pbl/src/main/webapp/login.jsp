<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Community Service System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script defer src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<div class="auth-shell">
    <div class="auth-card panel">
        <a class="back-link" href="index.jsp">Back to home</a>
        <h2>Login</h2>
        <p>Access your dashboard to manage requests and assignments.</p>

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

        <form class="auth-form" action="${pageContext.request.contextPath}/auth" method="post" autocomplete="off" onsubmit="return validateLoginForm(this)">
            <input type="hidden" name="action" value="login">
            <label>Email</label>
            <input type="email" name="email" placeholder="Enter email" required>
            <label>Password</label>
            <input type="password" name="password" autocomplete="new-password" placeholder="Enter password" required>
            <button class="btn-primary full-width" type="submit">Login</button>
        </form>
        <p class="auth-footer">New here? <a href="register.jsp">Create an account</a></p>
    </div>
</div>
</body>
</html>
