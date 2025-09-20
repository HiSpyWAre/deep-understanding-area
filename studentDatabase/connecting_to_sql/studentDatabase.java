// package projectLadderJava;
// Connect into MySQL workbench and change the java method with SQL syntax

import java.sql.*;
import java.util.Scanner;
import com.mysql.cj.jdbc.Driver;

public class studentDatabase {

    // Connection setup
    private static Connection getConnection() throws SQLException {
    String url = "jdbc:mysql://localhost:3306/studentdb";
    String user = "";        // (MySQL username)
    String password = ""; // (real password)

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        System.out.println("MySQL JDBC Driver not found.");
        e.printStackTrace();
    }

    return DriverManager.getConnection(url, user, password);
}

    private static void insertStudent(int id, String name, String major, double gpa) {
        String sql = "INSERT INTO students (id, name, major, gpa) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, major);
            stmt.setDouble(4, gpa);
            stmt.executeUpdate();
            System.out.println("Student added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showStudents() {
        String sql = "SELECT * FROM students";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getString("major") + " | " +
                                   rs.getDouble("gpa"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateStudent(int id, double gpa) {
        String sql = "UPDATE students SET gpa=? WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, gpa);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Student Database Menu =====");
            System.out.println("1. Insert student");
            System.out.println("2. Show all students");
            System.out.println("3. Update student GPA");
            System.out.println("4. Delete student");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Major: ");
                    String major = scanner.nextLine();
                    System.out.print("Enter GPA: ");
                    double gpa = scanner.nextDouble();
                    insertStudent(id, name, major, gpa);
                    break;

                case 2:
                    showStudents();
                    break;

                case 3:
                    System.out.print("Enter Student ID to update GPA: ");
                    int updateId = scanner.nextInt();
                    System.out.print("Enter new GPA: ");
                    double newGpa = scanner.nextDouble();
                    updateStudent(updateId, newGpa);
                    break;

                case 4:
                    System.out.print("Enter Student ID to delete: ");
                    int deleteId = scanner.nextInt();
                    deleteStudent(deleteId);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice, try again!");
            }
        } while (choice != 5);

        scanner.close();
    }
}
