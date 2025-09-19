// package projectLadderJava;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

class Students {
    int ID;
    String Name;
    String Major;
    float GPA;

    Students(int ID, String Name, String Major, float GPA) {
        this.ID = ID;
        this.Name = Name;
        this.Major = Major;
        this.GPA = GPA;
    }

    public String toCSVString(){
        return ID + "," + Name + "," + Major + "," + GPA;
    }
}

public class studentDatabase {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // try {
        // Class.forName("com.mysql.cj.jdbc.Driver");
        // Connection conn = DriverManager.getConnection(
        //     "jdbc:mysql://localhost:3306/studentdb", "root", "your_password"
        // );
        // System.out.println("Connected to MySQL successfully!");
        
        // You can now use 'conn' inside your switch cases for queries (INSERT, SELECT, UPDATE, etc.)

        } catch (Exception e) {
            e.printStackTrace();
            return; // stop program if DB fails
        }
        List<Students> allStudent = new ArrayList<>();
        Map<String, List<Students>> data = new HashMap<>();

        System.out.println("   =========================================");
        System.out.println("   |            WELCOME TO THE             |");
        System.out.println("   |      STUDENT DATABASE MANAGEMENT      |");
        System.out.println("   =========================================\n");

        while (true) {
            System.out.println("1. Add New Student");
            System.out.println("2. Update GPA");
            System.out.println("3. Search and View Student Info");
            System.out.println("4. Class Statistics");
            System.out.println("5. Save & Exit");
            System.out.println("Select the menu : ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                try{
                    // void addStudent();
                    System.out.println("== Add New Student ==");
                    System.out.println("(fill out the student's identity below)");
                    System.out.print("Name = ");
                    String name = sc.nextLine();
                    System.out.print("ID = ");
                    int ID = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Major = ");
                    String major = sc.nextLine();
                    System.out.print("GPA = ");
                    float GPA = sc.nextFloat();
                    sc.nextLine();

                    Students student = new Students(ID, name, major, GPA);

                    allStudent.add(student);

                    data.putIfAbsent(name, new ArrayList<>());
                    data.get(name).add(student);
                }
                catch(Error InputMismatchException){
                    System.out.println("Invalid Input!");
                }
                    break;

                case 2: // update GPA
                    System.out.println("== Update Student's GPA ==");
                    System.out.println("Enter your ID = ");
                    // List<Students> id = new ArrayList<>();
                    int id = sc.nextInt();
                    sc.nextLine();
                    boolean found = false;
                    // if(!id.isEmpty())
                    for (Students e : allStudent) {
                        if (e.ID == id) {
                            System.out.println("Enter the newest GPA : ");
                            float newGPA = sc.nextFloat();
                            sc.nextLine();

                            e.GPA = newGPA;
                            System.out.println("GPA updated successfully for " + e.Name);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("GPA not found! Try again or Add data!");
                    }
                    break;

                case 3: // Search and View Student Info
                    System.out.println("== Search and View Student Info ==");
                    System.out.println("Enter your ID = ");
                    int search_id = sc.nextInt();
                    sc.nextLine();
                    boolean found2 = false;
                    // if(!id.isEmpty())
                    for (Students e : allStudent) {
                        if (e.ID == search_id) {
                            System.out.println(
                                    "ID: " + e.ID + " | Name: " + e.Name + " | Major: " + e.Major + " | GPA: " + e.GPA);
                            found2 = true;
                            break;
                        }
                    }
                    if (!found2) {
                        System.out.println("Student with that ID not found! Try again or Add data!");
                    }
                    break;

                case 4:
                    System.out.println("== Class Statistics ==");

                    if (allStudent.isEmpty()){
                        System.out.println("No Student Data to show statistic. Please add student first.");
                    }
                    // show total student number
                    System.out.println("Total Students Enrolled  = " + allStudent.size());

                    // gpa specific statistic
                    float highestGPA = 0.0f;
                    float lowestGPA = 4.0f;

                    String maxGPA = "";
                    String minGPA = "";
                    
                    Map<String, Integer> studentPerMajor = new HashMap<>();
                    Map<String, Float> gpaPerMajor = new HashMap<>();

                    float count = 0.0f;
                    for (Students e : allStudent) {
                        count += e.GPA;
                        
                    
                        if (e.GPA > highestGPA) {
                            highestGPA = e.GPA;
                            maxGPA = e.Name;
                        }

                        if (e.GPA < lowestGPA) {
                            lowestGPA = e.GPA;
                            minGPA = e.Name;
                        }
                        
                        String currentMajor = e.Major;
                        // getOrDefault is a safe way to get a value or a default (0) if it's not in the map yet.
                        studentPerMajor.put(currentMajor, studentPerMajor.getOrDefault(currentMajor, 0) + 1);
                        gpaPerMajor.put(currentMajor, gpaPerMajor.getOrDefault(currentMajor, 0.0f) + e.GPA);
                        
                    }
                    

                    float mean = count / allStudent.size();

                    System.out.println("---- GPA Information ----");
                    System.out.println("Average Class GPA: " + mean);
                    System.out.printf("The highest GPA : %.2f (Achieved by : %s)"+ "\n", highestGPA, maxGPA);
                    System.out.printf("Lowest GPA:  %.2f (Achieved by: %s)\n", lowestGPA, minGPA);

                    // major spesific statistic
                    System.out.println("---- Major Distribution ----");

                    for (String majorName : studentPerMajor.keySet()) {
                        int count2 = studentPerMajor.get(majorName);
                        float gpaSum = gpaPerMajor.get(majorName);
                        float majorAverageGpa = gpaSum / count2;
                        System.out.printf("- %s: %d students (Avg. GPA: %.2f)\n", majorName, count2, majorAverageGpa);
                    }
                    break;

                case 5:
                    try {
                        FileWriter writer = new FileWriter("database.txt");
                        System.out.println("Saving data....");
                        for (Students s : allStudent){
                            writer.write(s.toCSVString()+ "\n");
                        }
                        writer.close();

                        System.out.println("Data saved successfully");
                        System.out.println("Exiting program. Goodbye!");
                    }

                    catch (IOException a){
                        System.out.println("An error has occuredd. try again!");
                        a.printStackTrace();
                    }
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }

        }
    }

}
