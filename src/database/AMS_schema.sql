CREATE DATABASE IF NOT EXISTS ams_db;
USE ams_db;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'LECTURER') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Courses Table
CREATE TABLE IF NOT EXISTS courses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Students Table
CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    reg_no VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    course_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE SET NULL
);

-- Lecturers Table
CREATE TABLE IF NOT EXISTS lecturers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    emp_no VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    subject VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Class Schedules Table
CREATE TABLE IF NOT EXISTS class_schedules (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT NOT NULL,
    lecturer_id INT NOT NULL,
    subject VARCHAR(100) NOT NULL,
    date DATE NOT NULL,
    time VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    FOREIGN KEY (lecturer_id) REFERENCES lecturers(id) ON DELETE CASCADE
);

-- Attendance Table
CREATE TABLE IF NOT EXISTS attendance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    class_id INT NOT NULL,
    status ENUM('PRESENT', 'ABSENT') NOT NULL,
    marked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (class_id) REFERENCES class_schedules(id) ON DELETE CASCADE,
    UNIQUE KEY unique_attendance (student_id, class_id)
);

-- ==============================================================
-- INSERT SAMPLE DATA
-- ==============================================================

-- Insert default users
INSERT INTO users (username, password, role) VALUES 
('admin', 'admin123', 'ADMIN'),
('lecturer1', 'lect123', 'LECTURER');

-- Insert sample courses
INSERT INTO courses (code, name, description) VALUES 
('CS101', 'Introduction to Programming', 'Basic programming concepts'),
('CS102', 'Data Structures', 'Advanced data structures and algorithms'),
('CS201', 'Database Systems', 'Relational database design and SQL'),
('CS301', 'Software Engineering', 'Software development methodologies');

-- Insert sample students
INSERT INTO students (reg_no, name, email, phone, course_id) VALUES 
('STU001', 'John Doe', 'john@email.com', '1234567890', 1),
('STU002', 'Jane Smith', 'jane@email.com', '0987654321', 1),
('STU003', 'Bob Johnson', 'bob@email.com', '1122334455', 2),
('STU004', 'Alice Williams', 'alice@email.com', '5544332211', 2),
('STU005', 'Charlie Brown', 'charlie@email.com', '6677889900', 1),
('STU006', 'Diana Prince', 'diana@email.com', '9988776655', 3);

-- Insert sample lecturers
INSERT INTO lecturers (emp_no, name, email, phone, subject) VALUES 
('EMP001', 'Dr. Michael Brown', 'michael@email.com', '1231231234', 'Computer Science'),
('EMP002', 'Prof. Sarah Davis', 'sarah@email.com', '4564564567', 'Software Engineering'),
('EMP003', 'Dr. Robert Wilson', 'robert@email.com', '7897897890', 'Database Systems');

-- Insert sample class schedules
INSERT INTO class_schedules (course_id, lecturer_id, subject, date, time) VALUES 
(1, 1, 'Programming Basics', '2025-11-05', '10:00 AM'),
(1, 1, 'Control Structures', '2025-11-06', '10:00 AM'),
(2, 2, 'Arrays and Lists', '2025-11-05', '02:00 PM'),
(3, 3, 'SQL Fundamentals', '2025-11-07', '09:00 AM');

-- Insert sample attendance records
INSERT INTO attendance (student_id, class_id, status, marked_at) VALUES 
(1, 1, 'PRESENT', '2025-11-05 10:05:00'),
(2, 1, 'PRESENT', '2025-11-05 10:05:00'),
(5, 1, 'ABSENT', '2025-11-05 10:05:00'),
(3, 3, 'PRESENT', '2025-11-05 14:05:00'),
(4, 3, 'PRESENT', '2025-11-05 14:05:00');

-- ==============================================================
-- CREATE INDEXES FOR BETTER PERFORMANCE
-- ==============================================================

CREATE INDEX idx_student_course ON students(course_id);
CREATE INDEX idx_attendance_student ON attendance(student_id);
CREATE INDEX idx_attendance_class ON attendance(class_id);
CREATE INDEX idx_schedule_course ON class_schedules(course_id);
CREATE INDEX idx_schedule_date ON class_schedules(date);

-- ==============================================================
-- CREATE VIEWS FOR REPORTING
-- ==============================================================

-- Student Attendance Summary View
CREATE OR REPLACE VIEW student_attendance_summary AS
SELECT 
    s.id AS student_id,
    s.reg_no,
    s.name,
    c.name AS course_name,
    COUNT(a.id) AS total_classes,
    SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) AS present_count,
    SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END) AS absent_count,
    ROUND((SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END)  100.0)  COUNT(a.id), 2) AS attendance_percentage
FROM students s
LEFT JOIN courses c ON s.course_id = c.id
LEFT JOIN attendance a ON s.id = a.student_id
GROUP BY s.id, s.reg_no, s.name, c.name;

-- Class Attendance Detail View
CREATE OR REPLACE VIEW class_attendance_detail AS
SELECT 
    cs.id AS class_id,
    cs.date,
    cs.time,
    cs.subject,
    c.name AS course_name,
    l.name AS lecturer_name,
    s.reg_no,
    s.name AS student_name,
    COALESCE(a.status, 'NOT_MARKED') AS status
FROM class_schedules cs
JOIN courses c ON cs.course_id = c.id
JOIN lecturers l ON cs.lecturer_id = l.id
CROSS JOIN students s
LEFT JOIN attendance a ON a.class_id = cs.id AND a.student_id = s.id
WHERE s.course_id = cs.course_id
ORDER BY cs.date DESC, cs.time, s.name;