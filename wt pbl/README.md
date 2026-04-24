# Community Service System

A beginner-friendly JSP/Servlet/MySQL web application that connects volunteers with people who need help.

## Features
- User registration and login for Requester, Volunteer, and Admin roles
- Dashboard with user info, active requests, assigned tasks, and stats
- Post service requests with category, location, and contact details
- Volunteer request board with accept/reject actions
- Admin panel for managing users, requests, completed services, and feedback
- Status tracking: Pending, Accepted, Completed
- Feedback form to rate volunteers
- Responsive UI with CSS and basic JavaScript validation

## Project Structure
```text
community-service-system/
├── pom.xml
├── README.md
├── sql/
│   ├── schema.sql
│   └── sample_data.sql
└── src/
    └── main/
        ├── java/com/communityservice/
        │   ├── dao/
        │   ├── model/
        │   ├── servlet/
        │   └── util/
        └── webapp/
            ├── css/style.css
            ├── js/main.js
            ├── index.jsp
            ├── login.jsp
            ├── register.jsp
            └── WEB-INF/views/
                ├── dashboard.jsp
                ├── post-request.jsp
                ├── view-requests.jsp
                └── admin-dashboard.jsp
```

## Requirements
- Java 8 or later
- Apache Maven 3.8+
- Apache Tomcat 9.x
- MySQL 8.x

## Database Setup
1. Create a MySQL database named `community_service_system`.
2. Run `sql/schema.sql` to create the tables.
3. Run `sql/sample_data.sql` to insert sample users and requests.
4. Update `src/main/java/com/communityservice/util/DBConnection.java` if your MySQL username or password is different.

## Run the Project
1. Open the project folder in VS Code or your IDE.
2. Build the WAR file:
   ```bash
   mvn clean package
   ```
3. Copy the generated `target/community-service-system.war` file to the Tomcat `webapps` folder.
4. Start Tomcat.
5. Open the app in your browser:
   ```text
   http://localhost:8080/community-service-system/
   ```

## Sample Login Accounts
- Admin: `admin@community.com` / `admin123`
- Requester: `priya@example.com` / `priya123`
- Volunteer: `aman@example.com` / `aman123`

## Notes
- This is designed as a simple PBL project and uses plain-text passwords for ease of setup.
- For a production system, replace plain-text passwords with secure hashing and add stronger access controls.
