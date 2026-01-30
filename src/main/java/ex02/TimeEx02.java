package ex02;

import java.time.LocalDateTime;

public class TimeEx02 {
    public static void main(String[] args) {
        LocalDateTime ldt = LocalDateTime.now();

        // 년도
        System.out.println(ldt.getYear());
        // 월(1~12)
        System.out.println(ldt.getMonthValue());
        // 일
        System.out.println(ldt.getDayOfMonth());
        // 요일
        System.out.println(ldt.getDayOfWeek());
        // 시
        System.out.println(ldt.getHour());
        // 분
        System.out.println(ldt.getMinute());
        // 초
        System.out.println(ldt.getSecond());

        System.out.println("===============");

        // 1일 더하기
        LocalDateTime nextDay = ldt.plusDays(1);
        System.out.println("1일 후: " + nextDay);
        // 1일 빼기
        LocalDateTime prevDay = ldt.minusDays(1);
        System.out.println("1일 전: " + prevDay);

        // 1달 더하기
        LocalDateTime nextMonth = ldt.plusMonths(1);
        System.out.println("1달 후: " + nextMonth);

        // 1년 더하기
        LocalDateTime nextYear = ldt.plusYears(1);
        System.out.println("1년 후: " + nextYear);
    }
}
