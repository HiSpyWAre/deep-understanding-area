import sqlite3
import mysql.connector

conn = sqlite3.connect("students.db")
cursor = conn.cursor()

cursor.execute("INSERT INTO students (id, name, major, gpa) VALUES (?, ?, ?, ?)", 
              (1, "Alice", "CS", 3.9))
conn.commit()

cursor.execute("SELECT name, gpa FROM students ORDER BY gpa DESC LIMIT 1")
print(cursor.fetchone())

student_id = 1
cursor.execute("SELECT * FROM students WHERE id=?", (student_id,))
print(cursor.fetchone())

conn = mysql.connector.connect(
    host="localhost",
    user="root",
    password="your_password",
    database="studentdb"
)

cursor = conn.cursor()

cursor.execute("SELECT name, gpa FROM students ORDER BY gpa DESC LIMIT 1")
print(cursor.fetchone())

conn.close()