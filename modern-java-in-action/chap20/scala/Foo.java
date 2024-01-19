package chap20.scala;

import java.util.stream.IntStream;

public class Foo {
    public static void main(String[] args) {
        IntStream.rangeClosed(2, 6)
                .forEach(n -> System.out.println("Hello " + n + " bottles of beer"));
    }
}

/* 스칼라 구현
object Beer {
    def main(args: Array[String]) {
        2 to 6 foreach { n => println(s"Hello ${n} bottles of beer") }
    }
}
 */