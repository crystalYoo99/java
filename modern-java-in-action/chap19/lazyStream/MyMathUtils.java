package chap19.lazyStream;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MyMathUtils {
    public static void main(String[] args) {
        System.out.println(primes(25).map(String::valueOf).collect(Collectors.joining(", ")));

        // 자기 정의 스트림. 재귀 스트림 예제
        IntStream numbers = numbers();
        int head = head(numbers);
        IntStream filtered = tail(numbers).filter(n -> n % head != 0);
    }

    public static Stream<Integer> primes(int n) {
        return Stream.iterate(2, i -> i + 1)
                .filter(MyMathUtils::isPrime)
                .limit(n);
    }

    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt(candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }

    // 1단계 : 스트림 숫자 얻기
    static IntStream numbers() {
        return IntStream.iterate(2, n -> n + 1);
    }

    // 2단계 : 머리 획득
    static int head(IntStream numbers) {
        return numbers.findFirst().getAsInt();
    }

    // 3단계 : 꼬리 필터링
    static IntStream tail(IntStream numbers) {
        return numbers.skip(1);
    }

    // 4단계 : 재귀적으로 소수 스트림 생성
    // 에러 발생함. 스트림을 머리, 꼬리로 분리하는 findFirst, skip 사용함.
    // 최종 연산에 스트림 호출 시 스트림 완전 소비됨

    // IntStream.concat은 두개의 스트림 인스턴스를 인수로 받음
    // 두 번째 인수가 primes를 직접 재귀적으로 호출하면서 무한 재귀에 빠짐
    // 게으른 평가 사용해야 함. #::
    static IntStream primes(IntStream numbers) {
        int head = head(numbers);
        return IntStream.concat(
                IntStream.of(head),
                primes(tail(numbers).filter(n -> n % head != 0))
        );
    }
}