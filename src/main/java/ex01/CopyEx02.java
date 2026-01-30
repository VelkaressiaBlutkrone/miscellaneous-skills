package ex01;

import lombok.Data;

/**
 * User 데이터 전송 객체 (DTO) Ver. 2.
 * 생성자를 통해 객체 생성 시점에 모든 필드 값을 초기화합니다.
 */
@Data
class UserDto2{
    private Integer id;
    private String userName;
    private String email;

    /**
     * 모든 필드를 인자로 받는 생성자.
     * 이 생성자를 사용하면 객체를 생성하는 동시에 데이터 복사를 완료할 수 있습니다.
     * @param id 사용자 ID
     * @param userName 사용자 이름
     * @param email 사용자 이메일
     */
    public UserDto2(Integer id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

/**
 * 객체 복사 예제 2: 생성자를 사용하여 객체 복사하기
 * DTO 객체를 생성할 때 생성자에 원본 객체의 데이터를 전달하여 초기화하는 방법을 보여줍니다.
 */
public class CopyEx02 {
    public static void main(String[] args) {
        // 1. 원본 User 객체 생성 및 데이터 할당
        ex01.User user = new User();
        user.setId(1);
        user.setName("name");
        user.setEmail("name@gmail.com");

        // 2. UserDto2 객체 생성 시점에 생성자의 인자로 User 객체의 필드 값을 전달
        // new 키워드를 사용하는 한 줄로 객체 생성과 데이터 복사가 동시에 이루어집니다.
        // 이는 CopyEx01의 setter를 여러 번 호출하는 방식보다 코드가 간결합니다.
        UserDto2 dto = new UserDto2(user.getId(), user.getName(), user.getEmail());

        // 3. 복사된 데이터가 담긴 UserDto2 객체 출력
        System.out.println(dto);
    }
}
