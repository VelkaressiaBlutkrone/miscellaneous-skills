package ex01;

import lombok.Data;

/**
 * User 데이터 전송 객체 (DTO) Ver. 3.
 * 생성자가 원본 객체(User)를 직접 인자로 받아, 데이터 복사 로직을 캡슐화합니다.
 */
@Data
class UserDto3 {
    private Integer id;
    private String userName;
    private String email;

    /**
     * User 객체를 인자로 받는 생성자 (일종의 "복사 생성자").
     * 이 생성자 내부에서 필요한 모든 필드 값을 원본 객체로부터 가져와 할당합니다.
     * 이를 통해 DTO 객체로의 변환 책임을 DTO 클래스 자신이 갖게 됩니다.
     * @param user 원본 데이터가 담긴 User 객체
     */
    public UserDto3(User user) {
        this.id = user.getId();
        this.userName = user.getName();
        this.email = user.getEmail();
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
 * 객체 복사 예제 3: 복사 생성자를 사용하여 객체 복사하기
 * DTO 클래스에 원본 객체를 통째로 넘겨 DTO를 생성하는 방법을 보여줍니다.
 */
public class CopyEx03 {
    public static void main(String[] args) {
        // 1. 원본 User 객체 생성 및 데이터 할당
        User user = new User();
        user.setId(1);
        user.setName("name");
        user.setEmail("name@gmail.com");

        // 2. UserDto3 객체 생성 시점에 User 객체 자체를 인자로 전달
        // 데이터 복사에 필요한 모든 로직이 UserDto3 생성자 내부에 캡슐화되어 있으므로,
        // main 메서드의 코드가 매우 간결하고 객체지향적이 됩니다.
        // "user 객체로 userDto3를 만든다"는 의미가 명확하게 드러납니다.
        UserDto3 dto = new UserDto3(user);

        // 3. 복사된 데이터가 담긴 UserDto3 객체 출력
        System.out.println(dto);
    }
}
