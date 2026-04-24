USE community_service_system;

INSERT INTO users (name, email, password, role) VALUES
('Admin User', 'admin@community.com', 'admin123', 'ADMIN'),
('Priya Sharma', 'priya@example.com', 'priya123', 'REQUESTER'),
('Rahul Verma', 'rahul@example.com', 'rahul123', 'REQUESTER'),
('Aman Khan', 'aman@example.com', 'aman123', 'VOLUNTEER'),
('Neha Patel', 'neha@example.com', 'neha123', 'VOLUNTEER');

INSERT INTO requests (user_id, title, description, category, status, location, contact) VALUES
(2, 'Need Blood Donor', 'Urgent O+ blood donation required for surgery.', 'Blood Donation', 'PENDING', 'City Hospital, Block A', '9876543210'),
(3, 'Food Pack for Family', 'Need food support for 3 days due to job loss.', 'Food Help', 'ACCEPTED', 'Mahalaxmi Society', 'rahul@example.com'),
(2, 'Math Tuition for Student', 'Looking for a volunteer to teach mathematics online.', 'Teaching', 'COMPLETED', 'Online', 'priya@example.com');

INSERT INTO volunteers (user_id, request_id) VALUES
(4, 2),
(5, 3);

INSERT INTO feedback (user_id, volunteer_id, request_id, rating, comments) VALUES
(2, 5, 3, 5, 'Very helpful and polite.');
