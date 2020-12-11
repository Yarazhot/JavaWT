package dao;

public class Filter {

    private Integer testId;
    private String email;
    private Integer teacherId;
    private Integer studentId;

    public boolean existTestId() {
        return testId != null;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public boolean existEmail() {
        return email != null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean existTeacherId() {
        return teacherId != null;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public boolean existStudentId() {
        return studentId != null;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}
