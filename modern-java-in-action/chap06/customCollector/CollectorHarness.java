package chap06.customCollector;
import java.util.function.Consumer;
public class CollectorHarness {

    public static void main(String[] args) {
        //System.out.println("Partitioning done in: " + execute(PartitionPrimeNumbers::partitionPrimes) + " msecs");
        System.out.println("Partitioning done in: " + execute(PartitionPrimeNumbers::partitionPrimesWithCustomCollector) + " msecs");
    }

    private static long execute(Consumer<Integer> primePartitioner) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) { // 테스트를 10번 반복
            long start = System.nanoTime();
            primePartitioner.accept(1_000_000); // 백만 개의 숫자를 소수와 비소수로 분할한다.
            long duration = (System.nanoTime() - start) / 1_000_000; // duration을 밀리초 단위로 측정한다
            if (duration < fastest) {
                fastest = duration; // 가장 빨리 실행되었는지 확인한다
            }
            System.out.println("done in " + duration);
        }
        return fastest;
    }

}