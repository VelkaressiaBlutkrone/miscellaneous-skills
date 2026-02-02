package ex04;

import java.util.NoSuchElementException;
import java.util.Optional;

public class OptEx01 {
    public static void main(String[] args) {
        String name = "ssar";

        // Optional.of(value): null이 아닌 값을 포함하는 Optional 객체를 생성합니다.
        // 만약 value가 null이면 NullPointerException이 발생합니다.
        Optional<String> opt = Optional.of(name);

        // Optional.get(): Optional이 가지고 있는 값을 반환합니다.
        // 만약 Optional이 비어있으면 NoSuchElementException이 발생합니다. (주의해서 사용해야 합니다)
        System.out.println(opt.get());
        System.out.println(opt.get().length());

        String name2 = null;
        // Optional.ofNullable(value): null일 수도 있는 값을 Optional로 감쌉니다.
        // value가 null이면 비어있는 Optional을 반환하고, null이 아니면 값을 포함하는 Optional을 반환합니다.
        Optional<String> opt2 = Optional.ofNullable(name2);

        // Optional.isPresent(): Optional이 값을 가지고 있으면 true를 반환합니다.
        if(opt2.isPresent()){
            System.out.println(opt2.get());
        }

        // Optional.isEmpty(): Optional이 값을 가지고 있지 않으면 true를 반환합니다. (Java 11부터 사용 가능)
        // opt2가 비어있으므로 이 조건문은 true가 되어 RuntimeException이 발생합니다.
        // 따라서 이 코드는 실행 시점에서 예외를 발생시키도록 의도된 것입니다.
        if(opt2.isEmpty()){
            throw new RuntimeException("value is empty");
        }

        String name3 = null;

        // Optional.orElse(other): Optional이 값을 가지고 있으면 그 값을 반환하고,
        // 비어있으면 인자로 넘겨준 other 값을 반환합니다.
        // 여기서는 name3가 null이므로 "“"이 반환되어 Optional로 감싸집니다.
        // .describeConstable()은 Optional<String>을 반환합니다.
        Optional<String> opt3 = Optional.ofNullable(name3).orElse("").describeConstable();

        // Optional.orElseGet(supplier): Optional이 값을 가지고 있으면 그 값을 반환하고,
        // 비어있으면 supplier 함수를 실행하여 그 결과를 반환합니다.
        // orElse()와 다르게 supplier는 Optional이 비어있을 때만 실행됩니다. (성능상 이점)
        // 여기서는 name3가 null이므로 람다식이 실행되어 "“"이 반환되어 Optional로 감싸집니다.
        Optional<String> opt4 = Optional.ofNullable(name3).orElseGet(()-> "").describeConstable();

        // Optional.orElseThrow(): Optional이 값을 가지고 있으면 그 값을 반환하고,
        // 비어있으면 NoSuchElementException을 발생시킵니다. (인자 없는 버전, Java 10부터 사용 가능)
        // name3가 null이므로 여기서 NoSuchElementException이 발생하며, 이 아래 코드는 실행되지 않습니다.
        String result1 = Optional.ofNullable(name3).orElseThrow();

        // Optional.orElseThrow(exceptionSupplier): Optional이 값을 가지고 있으면 그 값을 반환하고,
        // 비어있으면 exceptionSupplier 함수를 실행하여 생성된 예외를 발생시킵니다.
        // name3가 null이므로 여기서 NoSuchElementException("error")가 발생합니다.
        String result2 = Optional.ofNullable(name3).orElseThrow(()-> new NoSuchElementException("error"));
    }
}
