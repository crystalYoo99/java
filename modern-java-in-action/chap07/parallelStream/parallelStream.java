package chap07.parallelStream;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class parallelStream {
    // 숫자 n을 인수로 받아서 합계를 반환하는 메서드. 스트림.
    public long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1) // 무한 자연수 스트림 생성
                .limit(n) // n개 이하로 제한
                .reduce(0L, Long::sum); //모든 숫자를 더하는 스트림 리듀싱 연산
    }

    // 전통적인 자바의 방식
    public long iterativeSum(long n) {
        long result = 0;
        for (long i = 1L; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel() //스트림을 병렬 스트림으로 변환
                .reduce(0L, Long::sum);
    }

    // 특화된 메서드 사용
    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(Long::sum).getAsLong();
    }

    // n까지의 자연수를 더하면서 공유된 누적자를 바꾸는 프로그램
    // 순차 실행할 수 있게 구현되어서 병렬로 실행시 문제 발생.
    // total 접근할 때마다 데이터 레이스 문제 발생
    public long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }
    public class Accumulator {
        public long total = 0;
        public void add(long value) { total += value; }
    }

    // 느리고 올바른 값 나오지 않음. 여러 스레드에서 동시에 누적자 접근하면  문제 발생.
    public long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }
}
