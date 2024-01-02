package chap09.debugging;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Peek {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5);
        // forEach를 호출하는 순간 전체 스트림이 소비
        numbers.stream()
                .map(x -> x + 17)
                .filter(x -> x % 2 == 0)
                .limit(3)
                .forEach(System.out::println);

        System.out.println("============================");

        List<Integer> result = Stream.of(2, 3, 4, 5)
                .peek(x -> System.out.println("taking from stream: " + x)) // 소스에서 처음 소비한 요소를 출력
                .map(x -> x + 17)
                .peek(x -> System.out.println("after map: " + x)) // map 동작 실행 결과 출력
                .filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter: " + x)) // filter 동작 후 선택된 숫자 출력
                .limit(3)
                .peek(x -> System.out.println("after limit: " + x)) // limit 동작 후 선택된 숫자 출력
                .collect(toList());
    }
}
