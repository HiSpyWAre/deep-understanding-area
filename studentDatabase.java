package projectLadderJava;

import java.util.*;

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
}

public class studentDatabase {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Students> allStudent = new ArrayList<>();
        Map<String, List<Students>> data = new HashMap<>();
        while (true) {
            System.out.println("1. Add New Student");
            System.out.println("2. Update GPA");
            System.out.println("3. Search and View Student Info");
            System.out.println("4. Class Statistics");
            System.out.println("5. Save & Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    // void addStudent();
                    System.out.println("== Add New Student ==");
                    System.out.println("(fill out the student's identity below)");
                    System.out.println("Name = ");
                    String name = sc.nextLine();
                    System.out.println("ID = ");
                    int ID = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Major = ");
                    String major = sc.nextLine();
                    System.out.println("GPA = ");
                    float GPA = sc.nextFloat();
                    sc.nextLine();

                    Students student = new Students(ID, name, major, GPA);

                    allStudent.add(student);

                    data.putIfAbsent(name, new ArrayList<>());
                    data.get(name).add(student);

                    // if (data.containsKey(name)) {
                    //     data.get(name).add(student);
                    // } else {
                    //     List<Students> newList = new ArrayList<>();
                    //     newList.add(student);
                    //     data.put(name, newList);
                    // }
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
                            System.out.println("ID: " + e.ID + " | Name: " + e.Name + " | Major: " + e.Major + " | GPA: " + e.GPA);
                            found = true;
                            break;
                        }
                    }
                    if (!found2) {
                        System.out.println("GPA not found! Try again or Add data!");
                    }
                    break;

                    case 4:
                    System.out.println("== Class Statistics ==");
                    System.out.println("Enter your ID = ");
                    int statistic = sc.nextInt();
                    sc.nextLine();
                    // show total student number
                    System.out.println("Total number = " + allStudent.size());
                    // gpa specific statistic
                     for (Students e : allStudent) {
                        e.ID + =  
                        if (e.ID == search_id) {
                            System.out.println("ID: " + e.ID + " | Name: " + e.Name + " | Major: " + e.Major + " | GPA: " + e.GPA);

                    
            }

        }
    }

}

