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

## Stream

Java 8에서 도입된 Stream API는 컬렉션(리스트, 셋 등)의 요소를 함수형 프로그래밍 스타일로 간결하고 효율적으로 처리하는 방법을 제공합니다. 데이터의 흐름(stream)을 만들고, 이 흐름에 중간 연산(intermediate operations)을 연결하여 데이터를 가공한 후, 최종 연산(terminal operation)으로 결과를 만들어냅니다. `ex03/StreamEx01.java` 파일에서 다양한 예제를 확인할 수 있습니다.

### 주요 특징
- **선언적 프로그래밍**: '무엇을' 할 것인지만 명시하고, '어떻게' 할 것인지는 내부적으로 처리합니다.
- **지연 평가 (Lazy Evaluation)**: 최종 연산이 호출되기 전까지 중간 연산은 실행되지 않습니다.
- **병렬 처리**: `parallelStream()`을 사용하여 간단하게 데이터 처리를 병렬화할 수 있습니다.
- **일회성**: 스트림은 한 번 사용하면 닫힙니다. 다시 사용하려면 새로 생성해야 합니다.

### 주요 기능 목록

#### 1. 스트림 생성 (Creation)

| 메서드 | 설명 |
| --- | --- |
| `stream()` | 컬렉션(List, Set 등)에서 스트림을 생성합니다. |
| `parallelStream()` | 병렬 처리가 가능한 스트림을 생성합니다. |
| `Stream.of(T... values)` | 주어진 값들로 스트림을 생성합니다. |
| `Stream.iterate(T seed, UnaryOperator<T> f)` | 초기값(seed)과 함수를 이용해 무한 순차 스트림을 생성합니다. (예: `1, 3, 5, ...`) |
| `Stream.generate(Supplier<T> s)` | `Supplier`를 이용해 무한 스트림을 생성합니다. (예: 랜덤 숫자) |
| `Stream.ofNullable(T t)` | null일 수도 있는 객체로부터 안전하게 스트림을 생성합니다. (null이면 빈 스트림) |
| `IntStream`, `LongStream`, `DoubleStream` | 기본형(primitive type)에 특화된 스트림으로, 성능상 이점이 있습니다. |
| `Optional.stream()` | `Optional` 객체로부터 스트림을 생성합니다. (값이 있으면 요소 1개, 없으면 빈 스트림) |

#### 2. 중간 연산 (Intermediate Operations)
중간 연산은 또 다른 스트림을 반환하므로, 여러 연산을 체인 형태로 연결할 수 있습니다.

| 메서드 | 설명 |
| --- | --- |
| `map(Function<T, R> mapper)` | 각 요소를 1:1로 변환합니다. `i -> i * 2` |
| `flatMap(Function<T, Stream<R>> mapper)` | 각 요소를 스트림으로 변환한 후, 모든 스트림을 하나의 스트림으로 평탄화합니다. |
| `mapMulti(BiConsumer<T, Consumer<R>> mapper)` | `flatMap`의 고성능/저수준 버전으로, 하나의 요소를 0개 또는 여러개로 매핑할 때 효율적입니다. (JDK 16+) |
| `filter(Predicate<T> predicate)` | 주어진 조건(Predicate)에 맞는 요소만 남깁니다. |
| `distinct()` | 중복된 요소를 제거합니다. `Object.equals()` 기준. |
| `sorted()` | 요소를 자연 순서(오름차순)로 정렬합니다. `Comparator`를 인자로 받아 정렬 기준을 지정할 수 있습니다. |
| `peek(Consumer<T> action)` | 스트림의 각 요소에 특정 동작을 수행합니다. 주로 디버깅 용도로 사용합니다. |
| `limit(long maxSize)` | 스트림의 요소 개수를 주어진 수로 제한합니다. |
| `skip(long n)` | 스트림의 처음 `n`개 요소를 건너뜁니다. |
| `takeWhile(Predicate<T> predicate)` | (주로 정렬된 스트림에서) 조건을 만족하는 동안 요소를 선택하고, 조건이 깨지면 중단합니다. |
| `dropWhile(Predicate<T> predicate)` | (주로 정렬된 스트림에서) 조건을 만족하는 동안 요소를 버리고, 조건이 깨지면 나머지 요소를 선택합니다. |
| `mapToInt`, `mapToLong`, `mapToDouble` | 객체 스트림(`Stream<T>`)을 기본형 특화 스트림으로 변환합니다. |

#### 3. 최종 연산 (Terminal Operations)
최종 연산은 스트림을 소모하여 최종 결과를 반환합니다.

| 메서드 | 설명 |
| --- | --- |
| `forEach(Consumer<T> action)` | 각 요소에 대해 지정된 동작을 수행합니다. 반환값이 없습니다. |
| `collect(Collector<T, A, R> collector)` | 스트림의 요소를 `List`, `Set`, `Map` 등 다른 자료구조로 수집합니다. |
| `toList()` | 스트림의 요소를 `List`로 수집합니다. (`collect(Collectors.toList())`의 축약형, JDK 16+) |
| `count()` | 스트림의 요소 개수를 반환합니다. |
| `reduce(...)` | 모든 요소를 하나의 결과로 누적 연산합니다. (예: `(a, b) -> a + b`) |
| `findFirst()` | 스트림의 첫 번째 요소를 `Optional<T>`로 반환합니다. |
| `findAny()` | 스트림의 임의의 요소 하나를 `Optional<T>`로 반환합니다. (병렬 처리에서 유리) |
| `allMatch`, `anyMatch`, `noneMatch` | 모든/하나라도/아무것도 조건에 맞는지 여부를 `boolean`으로 반환합니다. (Short-circuiting) |
| `sum()`, `average()`, `max()`, `min()` | 기본형 특화 스트림에서 사용할 수 있는 숫자 집계 연산입니다. |

`collect` 연산은 `Collectors` 클래스의 팩토리 메서드와 함께 사용하여 매우 강력하고 다양한 기능을 수행할 수 있습니다.
- `Collectors.toSet()`, `Collectors.toUnmodifiableSet()` : Set으로 수집
- `Collectors.joining(",")` : 문자열로 결합
- `Collectors.groupingBy(...)` : 특정 기준으로 그룹화하여 Map 생성
- `Collectors.partitioningBy(...)` : `boolean` 기준으로 2분할하여 Map 생성
- `Collectors.toMap(...)` : 스트림을 Map으로 변환
- `Collectors.teeing(...)` : 두 개의 Collector를 동시에 적용 후 결과를 합침 (JDK 12+)


## Optional




