package chap07.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;


// n까지의 자연수 덧셈 작업을 병렬로 수행하는 방법
// RecursiveTask를 상속받아서 포크/조인 프레임워크에서 사용할 태스크를 생성
public class ForkJoinSumCalculator  extends java.util.concurrent.RecursiveTask<Long>{
    private final long[] numbers; // 더할 숫자 배열
    private final int start; // 이 서브태스크에서 처리할 배열의 초기 위치와 최종 위치
    private final int end;
    public static final long THRESHOLD = 10_000; // 이 값 이하의 서브태스크는 더 이상 분할할 수 없다.

    // 메인 태스크를 생성할 때 사용할 공개 생성자
    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    // 메인 태스크의 서브태스크를 재귀적으로 만들 때 사용할 비공개 생성자
    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() { // RecursiveTask의 추상 메서드 오버라이드
        int length = end - start;  // 이 태스크에서 더할 배열의 길이
        if (length <= THRESHOLD) {
            return computeSequentially(); // 기준값과 같거나 작으면 순차적으로 결과 계산
        }
        // 배열의 첫번째 절반을 더하도록 서브 태스크 생성
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start+length/2);
        // ForkJoinPool의 다른 스레드로 새로 생성한 태스크를 비동기로 실행한다
        leftTask.fork();
        // 배열의 나머지 절반을 더하도록 서브 태스크 생성
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start+length/2, end);
        // 두번째 서브 태스크를 동기 실행. 이 때 추가로 분할이 일어날 수도 있다.
        Long rightResult = rightTask.compute();
        // 첫번째 서브 태스크의 결과를 읽거나 아직 결과가 없으면 기다림.
        Long leftResult = leftTask.join();

        // 두 서브 태스크 결과를 조합한 게 이 태스크의 결과
        return leftResult + rightResult;
    }

    // 더 분할할 수 없을 때 서브태스크의 결과를 계산하는 단순한 알고리즘
    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static long forkJoinSum(long n) {
        // n까지의 자연수 포함하는 배열 생성
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        // 생성된 배열을 ForkJoinSumCalculator의 생성자로 전달해서  ForkJoinTask 생성
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        // 생성한 태스크를 새로운 ForkJoinPool의 invoke 메서드로 전달.
        // ForkJoinPool에서 실행되는 마지막 invoke 메서드의 반환값은 ForkJoinSumCalculator에서 정의한 태스크의 결과
        return new ForkJoinPool().invoke(task);
    }
}
