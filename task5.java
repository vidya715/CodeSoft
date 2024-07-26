import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Class representing a course
class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private int enrolled;
    private String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = 0;
        this.schedule = schedule;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public String getSchedule() {
        return schedule;
    }

    public boolean isAvailable() {
        return enrolled < capacity;
    }

    public void enrollStudent() {
        if (isAvailable()) {
            enrolled++;
        }
    }

    public void removeStudent() {
        if (enrolled > 0) {
            enrolled--;
        }
    }

    @Override
    public String toString() {
        return String.format("Code: %s, Title: %s, Description: %s, Schedule: %s, Slots Available: %d",
                code, title, description, schedule, capacity - enrolled);
    }
}

// Class representing a student
class Student {
    private String id;
    private String name;
    private List<Course> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean registerCourse(Course course) {
        if (course.isAvailable() && !registeredCourses.contains(course)) {
            registeredCourses.add(course);
            course.enrollStudent();
            return true;
        }
        return false;
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.removeStudent();
            return true;
        }
        return false;
    }
}

// Main class to manage the course registration system
public class CourseRegistrationSystem {
    private Map<String, Course> courseCatalog;
    private Map<String, Student> studentDatabase;

    public CourseRegistrationSystem() {
        courseCatalog = new HashMap<>();
        studentDatabase = new HashMap<>();
        initializeCourses();
    }

    private void initializeCourses() {
        courseCatalog.put("CS101", new Course("CS101", "Introduction to Computer Science", "Basic computer science course", 30, "MWF 9:00-10:00 AM"));
        courseCatalog.put("MATH201", new Course("MATH201", "Calculus I", "Fundamentals of calculus", 25, "TTh 10:00-11:30 AM"));
        courseCatalog.put("PHYS301", new Course("PHYS301", "Physics I", "Introduction to classical mechanics", 20, "MWF 11:00 AM-12:00 PM"));
    }

    public void registerStudent(String id, String name) {
        if (!studentDatabase.containsKey(id)) {
            studentDatabase.put(id, new Student(id, name));
            System.out.println("Student registered successfully.");
        } else {
            System.out.println("Student ID already exists.");
        }
    }

    public void displayCourses() {
        System.out.println("Available Courses:");
        for (Course course : courseCatalog.values()) {
            System.out.println(course);
        }
    }

    public void registerForCourse(String studentId, String courseCode) {
        Student student = studentDatabase.get(studentId);
        Course course = courseCatalog.get(courseCode);

        if (student != null && course != null) {
            if (student.registerCourse(course)) {
                System.out.println("Successfully registered for " + course.getTitle() + ".");
            } else {
                System.out.println("Registration failed. Either the course is full or you are already registered.");
            }
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    public void dropCourse(String studentId, String courseCode) {
        Student student = studentDatabase.get(studentId);
        Course course = courseCatalog.get(courseCode);

        if (student != null && course != null) {
            if (student.dropCourse(course)) {
                System.out.println("Successfully dropped course " + course.getTitle() + ".");
            } else {
                System.out.println("Drop failed. You are not registered for this course.");
            }
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    public void displayStudentCourses(String studentId) {
        Student student = studentDatabase.get(studentId);
        if (student != null) {
            System.out.println("Courses registered by " + student.getName() + ":");
            for (Course course : student.getRegisteredCourses()) {
                System.out.println(course);
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseRegistrationSystem system = new CourseRegistrationSystem();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Register Student");
            System.out.println("2. Display Courses");
            System.out.println("3. Register for Course");
            System.out.println("4. Drop Course");
            System.out.println("5. Display Student Courses");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter student ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    system.registerStudent(id, name);
                    break;
                case 2:
                    system.displayCourses();
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    id = scanner.nextLine();
                    System.out.print("Enter course code: ");
                    String courseCode = scanner.nextLine();
                    system.registerForCourse(id, courseCode);
                    break;
                case 4:
                    System.out.print("Enter student ID: ");
                    id = scanner.nextLine();
                    System.out.print("Enter course code: ");
                    courseCode = scanner.nextLine();
                    system.dropCourse(id, courseCode);
                    break;
                case 5:
                    System.out.print("Enter student ID: ");
                    id = scanner.nextLine();
                    system.displayStudentCourses(id);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
