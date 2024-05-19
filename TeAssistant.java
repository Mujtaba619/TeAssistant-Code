import java.util.ArrayList;
import java.util.Date;
import java.util.List;

enum Role {
    TEACHER,
    STUDENT
}

class User {
    private String username;
    private String password;
    private String name;
    private String email;
    private Role role;

    public User(String username, String password, String name, String email, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public void register(String username, String password, Role role, String name, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.email = email;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void logout() {
        System.out.println("User logged out");
    }

    public void updateProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
        } else {
            System.out.println("Old password is incorrect");
        }
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }
}
class Teacher extends User {
    private List<Assignment> assignments = new ArrayList<>();
    private List<Quiz> quizzes = new ArrayList<>();
    private List<PresentationSlide> slides = new ArrayList<>();

    public Teacher(String username, String password, String name, String email) {
        super(username, password, name, email, Role.TEACHER);
    }

    public void createAssignment(String title, String description, Date dueDate) {
        Assignment assignment = new Assignment(assignments.size() + 1, title, description, dueDate, this);
        assignments.add(assignment);
    }

    public void updateAssignment(int assignmentId, String title, String description, Date dueDate) {
        for (Assignment assignment : assignments) {
            if (assignment.getAssignmentId() == assignmentId) {
                assignment.setTitle(title);
                assignment.setDescription(description);
                assignment.setDueDate(dueDate);
                break;
            }
        }
    }

    public void deleteAssignment(int assignmentId) {
        assignments.removeIf(assignment -> assignment.getAssignmentId() == assignmentId);
    }

    public void createQuiz(String title, int duration, List<Question> questions) {
        Quiz quiz = new Quiz(quizzes.size() + 1, title, duration, questions, this);
        quizzes.add(quiz);
    }

    public void updateQuiz(int quizId, String title, int duration, List<Question> questions) {
        for (Quiz quiz : quizzes) {
            if (quiz.getQuizId() == quizId) {
                quiz.setTitle(title);
                quiz.setDuration(duration);
                quiz.setQuestions(questions);
                break;
            }
        }
    }

    public void deleteQuiz(int quizId) {
        quizzes.removeIf(quiz -> quiz.getQuizId() == quizId);
    }

    public void gradeQuiz(int quizId, List<String> studentSubmissions) {
        for (Quiz quiz : quizzes) {
            if (quiz.getQuizId() == quizId) {
                System.out.println(quiz.getQuizResults(studentSubmissions));
                break;
            }
        }
    }

    public void createPresentationSlide(String title, String content) {
        PresentationSlide slide = new PresentationSlide(slides.size() + 1, title, content, this);
        slides.add(slide);
    }

    public void updatePresentationSlide(int slideId, String title, String content) {
        for (PresentationSlide slide : slides) {
            if (slide.getSlideId() == slideId) {
                slide.setTitle(title);
                slide.setContent(content);
                break;
            }
        }
    }

    public void deletePresentationSlide(int slideId) {
        slides.removeIf(slide -> slide.getSlideId() == slideId);
    }
}
class Student extends User {
    public Student(String username, String password, String name, String email) {
        super(username, password, name, email, Role.STUDENT);
    }

    public void submitAssignment(int assignmentId, String submission) {
        // Here we need access to assignments which are typically stored in a database or a higher-level class
        System.out.println("Assignment " + assignmentId + " submitted with submission: " + submission);
    }

    public void takeQuiz(int quizId) {
        // Here we need access to quizzes which are typically stored in a database or a higher-level class
        System.out.println("Quiz " + quizId + " taken");
    }
}
class Assignment {
    private int assignmentId;
    private String title;
    private String description;
    private Date dueDate;
    private Teacher createdBy;

    public Assignment(int assignmentId, String title, String description, Date dueDate, Teacher createdBy) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.createdBy = createdBy;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public String getAssignmentDetails() {
        return "Title: " + title + ", Description: " + description + ", Due Date: " + dueDate;
    }

    public void updateDueDate(Date newDueDate) {
        this.dueDate = newDueDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
class Quiz {
    private int quizId;
    private String title;
    private int duration;
    private List<Question> questions;
    private Teacher createdBy;

    public Quiz(int quizId, String title, int duration, List<Question> questions, Teacher createdBy) {
        this.quizId = quizId;
        this.title = title;
        this.duration = duration;
        this.questions = questions;
        this.createdBy = createdBy;
    }

    public int getQuizId() {
        return quizId;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(int questionId) {
        questions.removeIf(q -> q.getQuestionId() == questionId);
    }

    public void updateDuration(int newDuration) {
        this.duration = newDuration;
    }

    public String getQuizResults(List<String> studentSubmissions) {
        // Assuming we simply list the submissions here. Grading logic would be more complex.
        return "Quiz Results: " + studentSubmissions.toString();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
class Question {
    private static int nextId = 1; // Static variable to auto-increment questionId
    private int questionId;
    private String questionText;
    private List<String> options;
    private String correctAnswer;

    public Question(String questionText, List<String> options, String correctAnswer) {
        this.questionId = nextId++;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void updateQuestionText(String newQuestionText) {
        this.questionText = newQuestionText;
    }

    public void updateOptions(List<String> newOptions) {
        this.options = newOptions;
    }

    public void updateCorrectAnswer(String newCorrectAnswer) {
        this.correctAnswer = newCorrectAnswer;
    }
}
class PresentationSlide {
    private int slideId;
    private String title;
    private String content;
    private Teacher createdBy;

    public PresentationSlide(int slideId, String title, String content, Teacher createdBy) {
        this.slideId = slideId;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }

    public int getSlideId() {
        return slideId;
    }

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    public void updateContent(String newContent) {
        this.content = newContent;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
public class TeAssistant {
    public static void main(String[] args) {
        Teacher teacher = new Teacher("teacher1", "pass123", "John Doe", "john@example.com");
        Student student = new Student("student1", "pass123", "Jane Smith", "jane@example.com");

        teacher.createAssignment("Math Homework", "Complete exercises 1-10", new Date());
        teacher.createQuiz("Math Quiz", 60, new ArrayList<>());

        teacher.createPresentationSlide("Introduction to Algebra", "This slide covers basic algebraic concepts.");

        student.submitAssignment(1, "My homework submission");
        student.takeQuiz(1);

        teacher.updateAssignment(1, "Math Homework", "Complete exercises 1-20", new Date());
        teacher.updateQuiz(1, "Math Quiz", 90, new ArrayList<>());

        teacher.updatePresentationSlide(1, "Advanced Algebra", "This slide covers advanced algebraic concepts.");

        teacher.gradeQuiz(1, new ArrayList<>());
        
        teacher.deleteAssignment(1);
        teacher.deleteQuiz(1);
        teacher.deletePresentationSlide(1);
    }
}
