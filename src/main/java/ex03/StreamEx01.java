package ex03;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

/**
 * Java Stream API 상세 예제
 * Stream API는 데이터 컬렉션을 함수형 스타일로 처리하는 방법을 제공합니다.
 * 이 클래스는 Stream API의 주요 중간 연산 및 최종 연산에 대한 상세한 설명과 예제를 포함합니다.
 */
public class StreamEx01 {

    public static void main(String[] args) {

        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println("원본 리스트 = " + list);

        // =====================================================
        // map : 각 요소를 새로운 요소로 1:1 변환 (타입이 바뀔 수도 있음)
        // 스트림의 크기는 그대로 유지됩니다.
        // =====================================================
        var doubled = list.stream()
                .map(i -> i * 2) // 각 Integer 요소를 2배가 된 Integer 요소로 변환
                .toList();
        System.out.println("map (각 요소 2배) = " + doubled);


        // =====================================================
        // filter : 조건에 맞는 요소만으로 구성된 새로운 스트림 반환
        // 스트림의 크기는 조건에 따라 줄어들 수 있습니다.
        // =====================================================
        var even = list.stream()
                .filter(i -> i % 2 == 0) // 짝수만 필터링 (true를 반환하는 요소만 통과)
                .toList();
        System.out.println("filter (짝수) = " + even);


        // =====================================================
        // peek : 스트림의 각 요소에 특정 작업을 수행 (주로 디버깅용)
        // 스트림 자체를 변경하지 않으며, 최종 연산이 호출되어야만 실행됩니다.
        // peek 안에서 스트림 요소의 상태를 변경하는 로직은 사용하지 않는 것이 좋습니다.
        // =====================================================
        System.out.print("peek 확인 (최종연산 count() 호출): ");
        list.stream()
                .peek(i -> System.out.print(i + " ")) // 각 요소를 화면에 출력하지만, 스트림 자체에는 영향 없음
                .count(); // 최종 연산. 이것이 없으면 peek은 실행조차 되지 않음.
        System.out.println();


        // =====================================================
        // distinct : 스트림 내의 중복 요소 제거 (Object.equals() 기준)
        // =====================================================
        var unique = Stream.of(1, 2, 2, 3, 3, 4)
                .distinct() // 중복된 2와 3을 하나씩만 남김
                .toList();
        System.out.println("distinct (중복 제거) = " + unique);


        // =====================================================
        // sorted : 요소를 정렬. 기본은 오름차순.
        // Comparator.reverseOrder() 와 같은 Comparator를 인자로 주어 정렬 기준을 변경할 수 있습니다.
        // =====================================================
        var desc = list.stream()
                .sorted(Comparator.reverseOrder()) // 내림차순 정렬
                .toList();
        System.out.println("sorted (내림차순) = " + desc);


        // =====================================================
        // skip + limit : 스트림의 일부를 잘라냄 (페이징 처리에 유용)
        // =====================================================
        var page = list.stream()
                .skip(3) // 앞 3개 요소를 건너뜀 (1,2,3 무시)
                .limit(3) // 이후 3개 요소만 취함 (4,5,6 선택)
                .toList();
        System.out.println("skip, limit (페이징) = " + page);


        // =====================================================
        // 기본형(Primitive) 특화 스트림 : IntStream, LongStream, DoubleStream
        // 불필요한 오토박싱/언박싱을 피해 성능상 이점을 가집니다. (Integer <-> int)
        // sum(), average() 같은 숫자 전용 최종 연산을 제공합니다.
        // =====================================================
        // list.stream() -> Stream<Integer>, .mapToInt() -> IntStream
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        // average()는 OptionalDouble을 반환하므로, 값이 없을 경우(orElse)를 대비해야 합니다.
        double avg = list.stream().mapToInt(Integer::intValue).average().orElse(0);
        // max()는 OptionalInt를 반환하므로, 값이 없을 경우 예외를 던지거나(orElseThrow) 기본값을 지정할 수 있습니다.
        int max = list.stream().mapToInt(Integer::intValue).max().orElseThrow();

        System.out.println("기본형 스트림 (sum=" + sum + ", avg=" + avg + ", max=" + max + ")");


        // =====================================================
        // collect : 스트림의 요소를 원하는 자료구조로 변환하는 최종 연산
        // Collectors 클래스의 다양한 팩토리 메서드를 사용합니다.
        // =====================================================
        // toUnmodifiableSet(): 짝수만 필터링하여 수정 불가능한 Set으로 수집 (중복 자동 제거)
        Set<Integer> evenSet = list.stream()
                .filter(i -> i % 2 == 0)
                .collect(Collectors.toUnmodifiableSet());

        // joining(): 각 요소를 문자열로 변환한 후, 지정된 구분자로 연결합니다.
        String joined = list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        System.out.println("collect (Set으로) = " + evenSet);
        System.out.println("collect (joining) = " + joined);


        // =====================================================
        // reduce : 모든 요소를 소모하여 하나의 값으로 누적 연산
        // 초기값(identity)과 누적 함수(accumulator)를 제공합니다.
        // sum(), count() 등 전용 집계 함수가 없을 때 사용합니다.
        // =====================================================
        int product = list.stream()
                .reduce(1, (accumulator, current) -> accumulator * current); // 1부터 시작해서 모든 요소를 곱해나감
        System.out.println("reduce (모든 요소의 곱) = " + product);


        // =====================================================
        // match 계열 : 스트림의 요소들이 특정 조건을 만족하는지 검사 (boolean 반환)
        // short-circuiting: 조건을 만족하는 즉시 연산을 중단하여 효율적입니다.
        // =====================================================
        boolean allEven = list.stream().allMatch(i -> i % 2 == 0); // 모든 요소가 짝수인가? -> false
        boolean anyGt5 = list.stream().anyMatch(i -> i > 5);   // 5보다 큰 요소가 하나라도 있는가? -> true
        boolean noneNegative = list.stream().noneMatch(i -> i < 0); // 음수가 하나도 없는가? -> true

        System.out.println("match (allEven=" + allEven + ", anyGt5=" + anyGt5 + ", noneNegative=" + noneNegative + ")");


        // =====================================================
        // find 계열 : 특정 요소를 검색 (Optional 반환)
        // short-circuiting: 요소를 찾는 즉시 연산을 중단합니다.
        // Optional로 결과를 감싸서, 결과가 없는 경우(null)를 안전하게 처리합니다.
        // =====================================================
        list.stream()
                .filter(i -> i % 2 == 0) // 짝수 중에서
                .findFirst() // 첫 번째 요소를 찾음 (순차 스트림에서는 항상 첫 번째 짝수인 2)
                .ifPresent(v -> System.out.println("find (첫 짝수) = " + v));


        // =====================================================
        // flatMap : 중첩된 구조를 한 단계 평탄화 (1-to-many 매핑)
        // Stream<List<String>> 같은 구조를 Stream<String> 으로 만드는데 사용됩니다.
        // =====================================================
        List<List<String>> nested = List.of(
                List.of("A", "B"),
                List.of("C", "D")
        );

        // [[A,B], [C,D]] -> [A,B,C,D]
        var flat = nested.stream() // Stream<List<String>>
                .flatMap(Collection::stream) // 각 List<String>을 Stream<String>으로 변환 후 하나의 Stream<String>으로 합침
                .toList();
        System.out.println("flatMap (중첩 리스트 평탄화) = " + flat);


        // =====================================================
        // mapMulti : flatMap의 고성능/저수준 버전 (JDK 16+)
        // 하나의 요소를 0개 또는 여러 개의 요소로 매핑할 때, 각 요소마다 새로운 스트림을 생성하는 flatMap보다 효율적입니다.
        // =====================================================
        var multi = Stream.of(1, 2, 3)
                .<Integer>mapMulti((number, consumer) -> {
                    consumer.accept(number); // 자기 자신을 포함
                    consumer.accept(number * 10); // 10을 곱한 수도 포함
                })
                .toList();
        System.out.println("mapMulti (1->n 매핑) = " + multi);


        // =====================================================
        // groupingBy : 특정 기준으로 요소를 그룹화하여 Map으로 반환
        // =====================================================
        // 짝수 여부(boolean)를 키로 하여 그룹화. {false=[1,3,5,7,9], true=[2,4,6,8]}
        var grouped = list.stream()
                .collect(Collectors.groupingBy(i -> i % 2 == 0));
        System.out.println("groupingBy (짝/홀수 그룹) = " + grouped);


        // =====================================================
        // partitioningBy : groupingBy의 특수한 경우. Predicate(boolean)를 기준으로 2분할.
        // groupingBy(i -> boolean) 보다 내부적으로 최적화되어 성능이 좋습니다.
        // =====================================================
        var partition = list.stream()
                .collect(Collectors.partitioningBy(i -> i > 5)); // 5보다 큰지 여부로 분할
        System.out.println("partitioningBy (5보다 큰지) = " + partition);


        // =====================================================
        // toMap : 스트림을 Map으로 변환. key/value 매핑 함수 필요.
        // 키 중복 시 예외가 발생하므로, 3번째 인자로 중복 처리 방법을 명시해야 안전합니다.
        // =====================================================
        var map = list.stream()
                .collect(Collectors.toMap(
                        Function.identity(), // 키 매퍼: 요소 그 자체를 키로 사용
                        i -> i * 100,        // 값 매퍼: 요소에 100을 곱한 값을 값으로 사용
                        (oldValue, newValue) -> newValue // 키 중복 시, 기존 값(oldValue)을 새 값(newValue)으로 덮어씀
                ));
        System.out.println("toMap = " + map);


        // =====================================================
        // teeing : 두 개의 다른 Collector로 스트림을 동시에 처리한 후, 두 결과를 합쳐서 최종 결과를 생성 (JDK 12+)
        // 스트림을 한 번만 순회하여 여러 집계(예: 개수와 합계)를 동시에 얻고 싶을 때 유용합니다.
        // =====================================================
        var stats = list.stream().collect(
                Collectors.teeing(
                        Collectors.counting(),              // 1. 요소의 개수를 센다
                        Collectors.summingInt(i -> i),      // 2. 요소의 합계를 구한다
                        (count, s) -> "총 개수=" + count + ", 총 합=" + s // 3. 두 결과를 합친다
                )
        );
        System.out.println("teeing (개수와 합계를 동시 집계) = " + stats);


        // =====================================================
        // takeWhile / dropWhile : 정렬된 스트림에서 특정 조건을 만족하는 동안 요소를 취하거나/버림 (JDK 9+)
        // =====================================================
        // takeWhile: i < 5 조건을 만족하는 동안(1,2,3,4) 요소를 취하고, 5에서 조건이 깨지면 즉시 중단.
        var take = list.stream()
                .takeWhile(i -> i < 5)
                .toList();

        // dropWhile: i < 5 조건을 만족하는 동안(1,2,3,4) 요소를 버리고, 5에서 조건이 깨지는 순간부터 모든 요소를 취함.
        var drop = list.stream()
                .dropWhile(i -> i < 5)
                .toList();

        System.out.println("takeWhile (5 이전까지) = " + take);
        System.out.println("dropWhile (5 이전까지 버림) = " + drop);


        // =====================================================
        // Optional → Stream 변환 (JDK 9+)
        // Optional 객체를 스트림으로 변환하여, 값이 있을 경우 1개, 없을 경우 0개의 요소를 가진 스트림을 얻습니다.
        // flatMap 등과 연계하여 null 처리를 깔끔하게 할 수 있습니다.
        // =====================================================
        Optional<String> opt = Optional.of("hello");
        var optList = opt.stream().toList();
        System.out.println("Optional -> Stream = " + optList);


        // =====================================================
        // parallelStream : 스트림을 내부적으로 여러 스레드로 나누어 병렬 처리
        // CPU 코어를 많이 사용하는 계산 집약적 작업(CPU-bound)에서 성능 향상을 기대할 수 있습니다.
        // I/O 작업이나 스레드 안전하지 않은 작업에는 부적합하며, 데이터가 적을 경우 오히려 성능이 저하될 수 있습니다.
        // =====================================================
        var parallel = list.parallelStream()
                .map(i -> i * i) // 각 요소를 병렬로 제곱
                .toList();
        System.out.println("parallelStream (병렬 제곱) = " + parallel);


        // =====================================================
        // iterate / generate : 무한 스트림 생성
        // 반드시 limit()을 함께 사용하여 스트림의 크기를 제한해야 합니다.
        // =====================================================
        // iterate: 이전 요소를 기반으로 다음 요소를 계산. (초기값, 다음 값 생성 함수)
        var seq = Stream.iterate(1, i -> i + 2) // 1부터 시작해서 2씩 더하기
                .limit(5) // 5개만 생성
                .toList();

        // generate: 이전 요소와 관계없이 Supplier를 통해 요소를 생성.
        var randoms = Stream.generate(Math::random) // 랜덤 숫자 생성
                .limit(3) // 3개만 생성
                .toList();

        System.out.println("iterate (무한 스트림) = " + seq);
        System.out.println("generate (무한 스트림) = " + randoms);


        // =====================================================
        // ofNullable : null일 수도 있는 객체를 안전하게 스트림으로 생성 (JDK 9+)
        // 객체가 null이면 빈 스트림, null이 아니면 요소가 1개인 스트림을 생성합니다.
        // =====================================================
        String nullable = null;
        var safe = Stream.ofNullable(nullable).toList(); // null을 주면 빈 리스트가 됨
        System.out.println("ofNullable (null 처리) = " + safe);

    }
}
