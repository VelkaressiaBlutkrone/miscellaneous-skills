package ex02;

class User{
    private Integer id;
    private String userName;
    private String password;
    private String type;

    private String classRoom;
    private String classYear;
    private String classTechName;

    private String subject;
    private String techName;

    private User(){};

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{");
        sb.append("id=").append(id);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", type='").append(type).append('\'');
        if ("student".equalsIgnoreCase(type)) {
            sb.append(", classRoom='").append(classRoom).append('\'');
            sb.append(", classYear='").append(classYear).append('\'');
            sb.append(", classTechName='").append(classTechName).append('\'');
        } else if ("teacher".equalsIgnoreCase(type)) {
            sb.append(", subject='").append(subject).append('\'');
            sb.append(", techName='").append(techName).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }

    public static User createTecher(int id, String userName, String password, String type, String subject, String techName){
        User user = new User();
        user.id = id;
        user.userName = userName;
        user.password = password;
        user.type = type;
        user.subject = subject;
        user.techName = techName;
        return user;
    }

    public static User createStudent(int id, String userName, String password, String type, String classRoom, String classYear, String classTechName){
        User user = new User();
        user.id = id;
        user.userName = userName;
        user.password = password;
        user.type = type;
        user.classRoom = classRoom;
        user.classYear = classYear;
        user.classTechName = classTechName;
        return user;
    }
}

public class NamedEx01 {
    public static void main(String[] args) {
        // 선생님 객체 생성
        User teacher = User.createTecher(1, "김선생", "pass123", "teacher", "수학", "수학선생님");
        System.out.println("선생님: " + teacher);

        // 학생 객체 생성
        User student = User.createStudent(101, "이학생", "studentpass", "student", "1학년 3반", "2024", "김선생");
        System.out.println("학생: " + student);
    }
}
