package com.communityservice.servlet;

import com.communityservice.dao.UserDAO;
import com.communityservice.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("register".equalsIgnoreCase(action)) {
            register(request, response);
        } else if ("login".equalsIgnoreCase(action)) {
            login(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if (isBlank(name) || isBlank(email) || isBlank(password) || isBlank(role)) {
            request.getSession().setAttribute("flashError", "Please fill in all registration fields.");
            response.sendRedirect(request.getContextPath() + "/register.jsp");
            return;
        }

        User user = new User();
        user.setName(name.trim());
        user.setEmail(email.trim());
        user.setPassword(password.trim());
        user.setRole(role.trim().toUpperCase());

        if (userDAO.createUser(user)) {
            request.getSession().setAttribute("flashMessage", "Registration successful. Please log in.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            request.getSession().setAttribute("flashError", "Email already exists or registration failed.");
            response.sendRedirect(request.getContextPath() + "/register.jsp");
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (isBlank(email) || isBlank(password)) {
            request.getSession().setAttribute("flashError", "Please enter your email and password.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = userDAO.login(email.trim(), password.trim());
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);
            session.setAttribute("flashMessage", "Welcome back, " + user.getName() + "!");

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/dashboard");
            }
        } else {
            request.getSession().setAttribute("flashError", "Invalid email or password.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
