package ex02;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class TimeEx01 {
    public static void main(String[] args) {
        // LocalDateTime: 날짜와 시간을 표현하며, 시간대(Time-zone) 정보는 포함하지 않습니다.
        // 시스템의 기본 시간대를 사용하여 현재 날짜와 시간을 가져옵니다.
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt); // 예: 2023-10-27T10:30:45.123

        // ZonedDateTime: 날짜, 시간, 그리고 시간대 정보를 모두 포함합니다.
        // 시스템의 기본 시간대를 사용하여 현재 날짜와 시간, 시간대 정보를 가져옵니다.
        ZonedDateTime zdt = ZonedDateTime.now();
        System.out.println(zdt); // 예: 2023-10-27T10:30:45.123+09:00[Asia/Seoul]

        // Instant: 에포크(Epoch, 1970-01-01T00:00:00Z)로부터의 순간을 나노초 단위로 표현합니다.
        // 주로 UTC(협정 세계시) 기준으로 특정 시점을 나타낼 때 사용됩니다.
        Instant instant = Instant.now();
        System.out.println(instant); // 예: 2023-10-27T01:30:45.123Z (UTC 기준)

        // Timestamp: java.sql 패키지에 속하며, 주로 데이터베이스와의 상호작용에 사용됩니다.
        // 에포크로부터 밀리초 단위의 시간을 인자로 받습니다.
        // 여기서는 1000밀리초 (1초)를 표현합니다.
        Timestamp timestamp = new Timestamp(1000);
        System.out.println(timestamp); // 예: 1970-01-01 09:00:01.0 (시스템 시간대 기준)

        // 현재 시스템의 밀리초를 사용하여 Timestamp 객체를 생성합니다.
        // System.currentTimeMillis()는 에포크로부터 현재까지의 밀리초를 반환합니다.
        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp2); // 예: 2023-10-27 10:30:45.123 (시스템 시간대 기준)

        // 시간 포맷 설명
        // 1. 2026-01-30T15:48:21.546269900
        //    - 구조: YYYY-MM-DD'T'HH:mm:ss.nnnnnnnnn
        //    - 이것은 ISO 8601 표준에 따른 로컬 날짜 및 시간 표현입니다.
        //    - 'T'는 날짜와 시간을 구분하는 구분자입니다.
        //    - 이 형식은 시간대 정보를 포함하지 않습니다. 따라서 '로컬' 시간으로 간주되며,
        //      어느 지역의 시간대인지에 대한 정보 없이는 전 세계적으로 유일한 특정 시점을 나타내지 못합니다.
        //      (예: 서울의 15시와 뉴욕의 15시는 다른 시간입니다.)
        //    - Java의 LocalDateTime 클래스가 이와 같은 시간대 정보 없는 시간을 나타냅니다.

        // 2. 2026-01-30T06:48:21.546269900Z
        //    - 구조: YYYY-MM-DD'T'HH:mm:ss.nnnnnnnnn'Z'
        //    - 이 또한 ISO 8601 표준에 따른 날짜 및 시간 표현입니다.
        //    - 마지막의 'Z'는 'Zulu' 시간을 의미하며, 이는 UTC(협정 세계시)와 동일합니다.
        //    - 'Z'는 시간 오프셋이 +00:00임을 나타냅니다.
        //    - 이 형식은 시간대 정보(UTC)를 포함하므로 전 세계적으로 유일한 특정 시점(절대 시간)을 나타냅니다.
        //      (예: 서울 시간(KST, UTC+9)으로 2026-01-30T15:48:21.546269900은
        //      UTC 시간으로 2026-01-30T06:48:21.546269900Z와 동일한 순간입니다.)
        //    - Java의 Instant 클래스나 ZonedDateTime 클래스가 이와 같은 절대 시간을 나타냅니다.
    }
}
