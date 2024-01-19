package chap20.scala;

import java.util.function.Function;
import java.util.stream.Stream;

public class Currying {
    public static void main(String[] args) {
        int r = multiply(2, 10);
        System.out.println(r);

        Stream.of(1, 3, 5, 7)
                .map(multiplyCurry(2))
                .forEach(System.out::println);
    }

    static int multiply(int x, int y) {
        return x * y;
    }

    static Function<Integer, Integer> multiplyCurry(int x) {
        return (Integer y) -> x * y;
    }
}

/* // scala 에서는 커리 분할 과정을 자동으로 처리하는 특수 문법 제공
// 전형적인 multiply 메서드
def multiply(x : Int, y: Int) = x * y
val r = multiply(2, 10);

// 커리 결과
def multiplyCurry(x :Int)(y : Int) = x * y //커리된 함수 정의
val r = multiplyCurry(2)(10) //커리된 함수 호출
 */