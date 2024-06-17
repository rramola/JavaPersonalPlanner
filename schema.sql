-- Users table
CREATE TABLE users (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    email TEXT NOT NULL UNIQUE,
    first_name TEXT,
    last_name TEXT
);

-- Bills table
CREATE TABLE bills (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    title TEXT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    due_date DATE NOT NULL,
    is_paid BOOLEAN DEFAULT FALSE,
    FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Calendar events table
CREATE TABLE calendar_events (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    title TEXT NOT NULL,
    event_description TEXT,
    event_date TIMESTAMP NOT NULL,
    event_location TEXT,
    FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- To-do items table
CREATE TABLE todo_items (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    title TEXT NOT NULL,
    todo_description TEXT,
    is_completed BOOLEAN DEFAULT FALSE,
    FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO users (email, first_name, last_name)
VALUES
('johndoe@example.com', 'John', 'Doe'),
('janedoe@example.com', 'Jane', 'Doe');

-- Insert sample bills
INSERT INTO bills (user_id, title, amount, due_date, is_paid)
VALUES
(1, 'Electricity Bill', 100.00, '2024-07-01', FALSE),
(1, 'Internet Bill', 50.00, '2024-07-05', TRUE),
(2, 'Water Bill', 30.00, '2024-07-10', FALSE);

-- Insert sample calendar events
INSERT INTO calendar_events (user_id, title, event_description, event_date, event_location)
VALUES
(1, 'Doctor Appointment', 'Annual check-up', '2024-06-20 10:00:00', 'Clinic'),
(1, 'Meeting with Client', 'Discuss project requirements', '2024-06-25 14:00:00', 'Office'),
(2, 'Birthday Party', 'Friends birthday celebration', '2024-06-30 18:00:00', 'Restaurant');

-- Insert sample to-do items
INSERT INTO todo_items (user_id, title, todo_description, is_completed)
VALUES
(1, 'Finish report', 'Complete the monthly report', FALSE),
(1, 'Grocery shopping', 'Buy groceries for the week', TRUE),
(2, 'Call plumber', 'Fix the kitchen sink', FALSE);

