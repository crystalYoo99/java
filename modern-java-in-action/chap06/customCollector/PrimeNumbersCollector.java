package chap06.customCollector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

import chap06.customCollector.PartitionPrimeNumbers;

// 1단계 : Collector 클래스 시그니처 정의
// public interface Collector<T, A, R>
// T는 스트림 요소의 형식, A는 누적자 형식, R은 수집 연산의 최종 결과 형식
public class PrimeNumbersCollector implements Collector<Integer,
        Map<Boolean, List<Integer>>,
        Map<Boolean, List<Integer>>> {

    // 2단계 : 리듀싱 연산 구현
    // supplier 메서드는 누적자를 만드는 함수를 반환
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>() {{
            // 두개의 빈 리스트를 포함하는 맵으로 수집 동작 시작
            put(true, new ArrayList<Integer>());
            put(false, new ArrayList<Integer>());
        }};
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
            acc.get(PartitionPrimeNumbers.isPrime(acc.get(true), // 지금까지 발견한 소수 리스트를 isPrime 메서드로 전달
                    candidate))
                    .add(candidate); // isPrime 메서드의 결과에 따라 맵에서 알맞은 리스트를 받아 현재 candidate를 추가
        };
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> { // 두 번째 맵을 첫 번째 맵에 병합
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));
            return map1;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity(); // 최종 수집 과정에서 데이터 변환이 필요하지 않으므로 항등 함수 반환
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
        // 발견한 소수의 순서에 의미가 있으므로 컬렉터는 IDENTITY_FINISH지만
        // UNORDERED, CONCURRENT는 아니다
    }
}
