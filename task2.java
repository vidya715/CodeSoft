import java.util.Scanner;

public class GradeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Take the number of subjects as input
        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();
        
        // Initialize variables
        int totalMarks = 0;
        int[] marks = new int[numSubjects];

        // Input marks for each subject
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter marks for subject " + (i + 1) + " (out of 100): ");
            marks[i] = scanner.nextInt();
            
            // Validate input
            while (marks[i] < 0 || marks[i] > 100) {
                System.out.println("Marks must be between 0 and 100. Please enter again.");
                System.out.print("Enter marks for subject " + (i + 1) + " (out of 100): ");
                marks[i] = scanner.nextInt();
            }
            
            totalMarks += marks[i];
        }

        // Calculate average percentage
        double averagePercentage = (totalMarks / (double)(numSubjects * 100)) * 100;

        // Calculate grade based on average percentage
        char grade = calculateGrade(averagePercentage);

        // Display results
        System.out.println("\nResults:");
        System.out.println("Total Marks: " + totalMarks);
        System.out.println("Average Percentage: " + String.format("%.2f", averagePercentage) + "%");
        System.out.println("Grade: " + grade);

        scanner.close();
    }

    private static char calculateGrade(double percentage) {
        if (percentage >= 90) {
            return 'A+';
        } else if (percentage >= 80) {
            return 'A';
        } else if (percentage >= 70) {
            return 'B+';
        } else if (percentage >= 60) {
            return 'B';
        } else if (percentage >= 50) {
            return 'C';
        } else {
            return 'F';
        }
    }
}
