package chap06.toListCollector;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import static java.util.stream.Collector.Characteristics.*;

public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {
    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new; // 수집 연산의 시발점
    }
    // 빈 리스트 반환

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add; // 탐색한 항목을 누적하고 바로 누적자를 고친다
    }
    // 이미 탐색한 항목을 포함하는 리스트에 현재 항목을 추가하는 연산을 수행

    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2); // 두 번째 콘텐츠와 합쳐서 첫 번째 누적자를 고친다.
            return list1; // 변경된 첫 번째 누적자를 반환한다.
        };
    }
    // 스트림의 두번째 서브 파트에서 수집한 항목 리스트를 첫번째 서브파트 결과 리스트 뒤에 추가

    @Override
    public Function<List<T>, List<T>> finisher() {
        return Function.identity(); // 항등 함수
    }
    // 누적자 객체가 이미 최종 결과. 변환 과정 필요 없어서 finisher 메서드는 항등 함수를 반환.

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(
                IDENTITY_FINISH, CONCURRENT)); // 컬렉터의 플래그를 IDENTITY_FINISH, CONCURRENT로 설정
    }
}