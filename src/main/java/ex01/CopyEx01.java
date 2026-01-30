package ex01;

import lombok.Data;

/**
 * User 데이터 전송 객체 (Data Transfer Object).
 * User 클래스의 데이터 중 일부(id, userName, email)를 담아 계층 간에 전달하는 데 사용됩니다.
 * 예를 들어, 전체 사용자 정보(비밀번호 포함)가 아닌, 일부 정보만 클라이언트에 보여주고 싶을 때 사용됩니다.
 */
@Data
class UserDto{
    private Integer id;
    private String userName;
    private String email;

    /**
     * 객체의 상태를 문자열로 표현하기 위해 toString() 메서드를 오버라이드합니다.
     * Lombok의 @Data 어노테이션이 자동으로 생성해주지만, 커스텀 포맷을 위해 직접 재정의할 수 있습니다.
     * @return 객체의 필드 값을 포함한 문자열
     */
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
 * 객체 복사 예제 1: 수동으로 필드 값 복사하기
 * 한 객체(User)의 데이터를 다른 객체(UserDto)로 옮기는 가장 기본적인 방법을 보여줍니다.
 */
public class CopyEx01 {
    public static void main(String[] args) {
        // 1. 원본 User 객체 생성 및 데이터 할당
        User user = new User();
        user.setId(1);
        user.setName("name");
        user.setEmail("name@gmail.com");

        // 2. 데이터를 전달받을 UserDto 객체 생성
        UserDto dto = new UserDto();

        // 3. User 객체의 필드 값을 UserDto 객체의 필드로 수동 복사
        // 각 필드에 대해 getter를 호출하고, 그 반환 값으로 setter를 호출합니다.
        dto.setId(user.getId());
        dto.setUserName(user.getName()); // 필드명이 다르므로 주의 (name -> userName)
        dto.setEmail(user.getEmail());

        // 4. 복사된 데이터가 담긴 UserDto 객체 출력
        System.out.println(dto);
    }
}
