-- Users table
CREATE TABLE users (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    email TEXT NOT NULL UNIQUE,
    firstName TEXT,
    lastName TEXT
);

-- Bills table
CREATE TABLE bills (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    userId INTEGER NOT NULL,
    title TEXT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    dueDate DATE NOT NULL,
    isPaid BOOLEAN DEFAULT FALSE,
    FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE
);

-- Calendar events table
CREATE TABLE calendar_events (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    userId INTEGER NOT NULL,
    title TEXT NOT NULL,
    eventDescription TEXT,
    eventDate TIMESTAMP NOT NULL,
    eventLocation TEXT,
    FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE
);

-- To-do items table
CREATE TABLE todo_items (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    userId INTEGER NOT NULL,
    title TEXT NOT NULL,
    todoDescription TEXT,
    isCompleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);
