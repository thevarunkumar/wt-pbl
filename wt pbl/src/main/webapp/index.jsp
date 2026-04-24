<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Community Service System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script defer src="${pageContext.request.contextPath}/js/main.js"></script>
</head>
<body>
<div class="bg-blobs"></div>
<header class="topbar">
    <div class="brand">
        <span class="brand-mark">CS</span>
        <div>
            <h1>Community Service System</h1>
            <p>Connect volunteers with people who need help</p>
        </div>
    </div>
    <nav class="nav-links">
        <a href="login.jsp">Login</a>
        <a href="register.jsp" class="btn-outline">Register</a>
    </nav>
</header>
<main class="hero-grid">
    <section class="hero-card panel accent">
        <span class="eyebrow">Simple JSP + Servlet + MySQL project</span>
        <h2>Support local communities with a clean, easy service workflow.</h2>
        <p>Request help, accept volunteering tasks, track statuses, and manage everything from one dashboard.</p>
        <div class="hero-actions">
            <a class="btn-primary" href="register.jsp">Get Started</a>
            <a class="btn-secondary" href="login.jsp">Existing User Login</a>
        </div>
        <div class="mini-stats">
            <div><strong>4</strong><span>Categories</span></div>
            <div><strong>3</strong><span>Status stages</span></div>
            <div><strong>1</strong><span>Unified portal</span></div>
        </div>
    </section>
    <aside class="panel info-card">
        <h3>Available Services</h3>
        <div class="service-tags">
            <span>Blood Donation</span>
            <span>Food Help</span>
            <span>Teaching</span>
            <span>Emergency</span>
        </div>
        <h3>Roles</h3>
        <ul class="icon-list">
            <li>Requester: post help requests</li>
            <li>Volunteer: accept or skip requests</li>
            <li>Admin: manage users and services</li>
        </ul>
    </aside>
</main>
<section class="features-grid">
    <article class="panel feature">
        <h3>Fast Requests</h3>
        <p>Requesters can submit title, description, location, contact, and category in a few clicks.</p>
    </article>
    <article class="panel feature">
        <h3>Volunteer Tracking</h3>
        <p>Volunteers can browse active requests and accept assignments from the request queue.</p>
    </article>
    <article class="panel feature">
        <h3>Admin Control</h3>
        <p>Admins can review users, mark completed services, and inspect feedback records.</p>
    </article>
</section>
</body>
</html>
