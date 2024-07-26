import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class Question {
    private String questionText;
    private List<String> options;
    private String correctAnswer;

    public Question(String questionText, List<String> options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public boolean isCorrectAnswer(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class Quiz {
    private List<Question> questions;
    private int score;
    private int totalQuestions;
    private List<Boolean> answers;

    public Quiz() {
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        score = 0;
        totalQuestions = 0;

        // Add questions to the quiz
        questions.add(new Question("What is the capital of France?",
                List.of("A) Paris", "B) Berlin", "C) London", "D) Madrid"), "A"));
        questions.add(new Question("What is 2 + 2?",
                List.of("A) 3", "B) 4", "C) 5", "D) 6"), "B"));
        questions.add(new Question("What is the largest planet in our solar system?",
                List.of("A) Earth", "B) Jupiter", "C) Mars", "D) Saturn"), "B"));

        totalQuestions = questions.size();
    }

    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < totalQuestions; i++) {
            Question question = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + question.getQuestionText());
            question.getOptions().forEach(System.out::println);

            long startTime = System.currentTimeMillis();
            String answer = getAnswerWithTimeout();
            long elapsedTime = System.currentTimeMillis() - startTime;

            if (elapsedTime > 15000) { // 15 seconds timer
                System.out.println("Time is up!");
                answer = ""; // No answer given due to timeout
            }

            boolean isCorrect = question.isCorrectAnswer(answer);
            if (isCorrect) {
                score++;
            }
            answers.add(isCorrect);

            System.out.println("Your answer: " + answer);
            if (isCorrect) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect. The correct answer was: " + question.getOptions().stream().filter(opt -> opt.startsWith(question.getCorrectAnswer())).findFirst().orElse("N/A"));
            }
        }
        displayResults();
    }

    private String getAnswerWithTimeout() {
        Scanner scanner = new Scanner(System.in);
        final String[] answer = new String[1];
        Thread inputThread = new Thread(() -> answer[0] = scanner.nextLine());

        inputThread.start();
        try {
            inputThread.join(15000); // 15 seconds timeout
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (answer[0] == null) {
            answer[0] = "";
        }
        return answer[0];
    }

    private void displayResults() {
        System.out.println("\nQuiz Completed!");
        System.out.println("Total Questions: " + totalQuestions);
        System.out.println("Correct Answers: " + score);
        System.out.println("Incorrect Answers: " + (totalQuestions - score));

        for (int i = 0; i < totalQuestions; i++) {
            System.out.println("Question " + (i + 1) + ": " + (answers.get(i) ? "Correct" : "Incorrect"));
        }
    }
}

public class QuizApplication {
    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.startQuiz();
    }
}
