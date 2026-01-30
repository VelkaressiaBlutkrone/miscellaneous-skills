# 스프링을 위한 잡기술 

## 객체복사

객체 지향 프로그래밍에서 한 객체의 데이터를 다른 객체로 복사해야 하는 경우는 흔하게 발생합니다. 특히, 데이터베이스와 상호작용하는 도메인 객체(Entity)를 클라이언트에게 전달하기 위한 데이터 전송 객체(DTO)로 변환할 때 자주 사용됩니다.

`ex01` 패키지에서는 다양한 객체 복사 방법을 보여줍니다.

### 1. 수동 복사 (Setter 사용)

`CopyEx01.java`에서는 가장 기본적인 방법인 `setter`를 이용하여 각 필드의 값을 직접 복사합니다.

```java
// User 객체 생성 및 초기화
User user = new User();
user.setId(1);
user.setName("name");
user.setEmail("name@gmail.com");

// UserDto 객체를 생성하고 User 객체의 값을 수동으로 복사
UserDto dto = new UserDto();
dto.setId(user.getId());
dto.setUserName(user.getName());
dto.setEmail(user.getEmail());
```

이 방법은 간단하지만, 필드가 많아지면 코드가 길어지고, 필드 변경 시 복사 코드를 누락할 위험이 있습니다.

### 2. 생성자를 이용한 복사

`CopyEx02.java`에서는 DTO의 생성자를 통해 객체를 복사합니다.

```java
User user = new User();
// ... user 초기화 ...

// DTO 생성자에 user 객체의 getter를 사용하여 값을 전달
UserDto2 dto = new UserDto2(user.getId(), user.getName(), user.getEmail());
```

이 방법은 DTO 객체의 불변성을 유지하는 데 도움이 되며, 객체 생성 시 모든 필요한 값이 전달되었는지 컴파일 타임에 확인할 수 있습니다.

### 3. 소스 객체를 받는 생성자를 이용한 복사

`CopyEx03.java`에서는 DTO의 생성자가 소스 객체(`User`)를 직접 받도록 하여 복사 로직을 캡슐화합니다.

```java
// DTO 생성자
public UserDto3(User user) {
    this.id = user.getId();
    this.userName = user.getName();
    this.email = user.getEmail();
}

// 사용
User user = new User();
// ... user 초기화 ...
UserDto3 dto = new UserDto3(user);
```

이 방식은 복사 로직이 DTO 내부에 존재하므로 코드가 더 깔끔해지고 재사용성이 높아집니다.

### 4. 복잡한 객체 및 컬렉션 복사

`CopyEx04.java`에서는 중첩된 객체와 컬렉션을 복사하는 방법을 보여줍니다. `Board` 객체는 `Reply` 객체 리스트를 가지고 있습니다.

```java
// DetailDto 생성자
public DetailDto(Board board) {
    this.boardId = board.getId();
    this.title = board.getTitle();
    this.content = board.getContent();
    // Reply 리스트를 String 리스트로 변환하여 복사
    this.comments = board.getReplies().stream().map(Reply::getComment).collect(Collectors.toList());
}
```

이 예제는 단순한 필드 복사뿐만 아니라, `stream`과 `map`을 사용하여 한 객체 리스트를 다른 형태의 리스트로 변환하는 복잡한 매핑 방법을 보여줍니다.

### 5. 정적 팩토리 메서드를 이용한 객체 생성

`ex02/NamedEx01.java`에서는 생성자 대신 정적 팩토리 메서드(Static Factory Method)를 사용하여 객체를 생성하는 방법을 보여줍니다. 이 패턴은 객체 생성의 목적을 명확하게 하고 생성 로직을 캡슐화하는 데 유용합니다.

```java
// User 클래스 내부
public static User createTeacher(int id, String userName, ...) {
    User user = new User();
    user.type = "teacher";
    // ... 필드 초기화 ...
    return user;
}

public static User createStudent(int id, String userName, ...) {
    User user = new User();
    user.type = "student";
    // ... 필드 초기화 ...
    return user;
}

// 사용
User teacher = User.createTeacher(1, "김선생", ...);
User student = User.createStudent(101, "이학생", ...);
```

이 방식은 다음과 같은 장점이 있습니다.
- **가독성**: `new User(...)`보다 `User.createTeacher(...)`처럼 메서드 이름이 생성 의도를 명확히 설명합니다.
- **유연성**: 매번 새로운 객체를 생성하는 대신, 기존에 생성된 객체를 반환하거나 하위 타입의 객체를 반환할 수 있어 유연성이 높습니다.
- **캡슐화**: 복잡한 생성 로직을 외부에 노출하지 않고 내부에 숨길 수 있습니다.

이러한 수동적인 방법들은 객체 구조가 복잡해질수록 번거롭고 오류 발생 가능성이 높습니다. 이런 단점을 보완하기 위해 `ModelMapper`나 `MapStruct` 같은 라이브러리를 사용하면 보일러플레이트 코드를 줄이고 객체 매핑을 자동화할 수 있습니다.

## UTC

UTC(협정 세계시, Coordinated Universal Time)는 전 세계적인 시간의 기준입니다. 서버 애플리케이션, 특히 여러 국가의 사용자를 대상으로 하는 서비스에서는 시간을 다룰 때 시간대(Timezone) 문제를 피하기 위해 UTC를 기준으로 시간을 저장하고 처리하는 것이 표준적인 방식입니다.

`ex02/TimeEx01.java`와 `TimeEx02.java`는 Java 8에서 도입된 `java.time` 패키지를 사용하여 시간과 날짜를 다루는 방법을 보여줍니다.

- **로컬 시간 (Local Time)**: 특정 지역의 시간을 의미하며, 시간대 정보가 없습니다. `LocalDateTime` 클래스가 이를 표현합니다.
  - 포맷 예: `2026-01-30T15:48:21.546`
  - 이 시간은 '서울'에서의 15시인지, '뉴욕'에서의 15시인지에 대한 정보가 없어, 그 자체만으로는 전 세계적인 특정 순간을 가리키지 못합니다.

- **절대 시간 (Absolute Time, UTC 기준)**: 전 세계적으로 동일한 특정 순간을 나타냅니다. `Instant` 또는 `ZonedDateTime` 클래스로 표현할 수 있습니다.
  - 포맷 예: `2026-01-30T06:48:21.546Z`
  - 마지막의 `Z`는 이 시간이 UTC 기준임을 나타내는 식별자입니다(Zulu time).
  - 예를 들어, 한국 시간(KST, UTC+9) `2026-01-30T15:48:21.546`은 UTC `2026-01-30T06:48:21.546Z`와 동일한 순간입니다.

`TimeEx01.java`에서는 이 클래스들의 차이점을 설명하고, `TimeEx02.java`에서는 `LocalDateTime` 객체에서 년, 월, 일, 시, 분, 초 등 특정 필드를 추출하거나 시간을 계산(`plusDays`, `minusDays` 등)하는 방법을 보여줍니다.

```java
// 로컬 시간 (시간대 정보 없음)
LocalDateTime ldt = LocalDateTime.now(); 

// 절대 시간 (UTC 기준)
Instant instant = Instant.now(); 

System.out.println(ldt);     // 예: 2026-01-30T15:48:21.546
System.out.println(instant); // 예: 2026-01-30T06:48:21.546Z
```

데이터베이스에 시간을 저장할 때는 `TIMESTAMP WITH TIME ZONE` 타입을 사용하고, 애플리케이션에서는 UTC `Instant`나 `ZonedDateTime`으로 다루는 것이 일반적입니다.

## Optional

## Stream


