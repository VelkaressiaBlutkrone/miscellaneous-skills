package ex01;

import lombok.Data;

/**
 * 사용자 정보를 담는 데이터 모델 클래스 (DTO or VO).
 * 이 클래스는 사용자의 기본 속성인 ID, 이름, 이메일, 비밀번호를 관리합니다.
 */
@Data // Lombok 라이브러리의 어노테이션으로, 아래 모든 필드에 대한 getter, setter, toString, equals, hashCode 메서드를 자동으로 생성해줍니다.
public class User {
    private Integer id; // 사용자의 고유 식별자
    private String name; // 사용자의 이름
    private String email; // 사용자의 이메일 주소
    private String password; // 사용자의 비밀번호
}
