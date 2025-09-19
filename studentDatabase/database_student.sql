CREATE DATABASE studentdb;
USE studentdb;

CREATE TABLE students (
	id INT PRIMARY KEY,
    name VARCHAR (100) NOT NULL,
    major VARCHAR (50),
    gpa FLOAT
);

INSERT INTO students (id, name, major, gpa)
VALUES 	(1, 'Alice', 'Computer Science', 3.0),
		(2, 'Rahma', 'DKV', 3.5),
        (3, 'Bondan', 'Medicine', 3.65);
        
SELECT * FROM students;

-- find top GPA
SELECT name, gpa FROM students ORDER BY gpa DESC LIMIT 1;

-- average GPA
SELECT AVG(gpa) AS avg_gpa FROM students;