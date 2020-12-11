package entity;

public class AnswerEntity extends Entity{
    private int questionId;
    private int studentId;
    private UserEntity student;
    private String text;

    public AnswerEntity() {
    }

    public AnswerEntity(String text, int questionId) {
        this.text = text;
        this.questionId = questionId;
    }

    public AnswerEntity(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public UserEntity getStudent() {
        return student;
    }

    public void setStudent(UserEntity student) {
        this.student = student;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
