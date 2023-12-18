package chap05.stream;

import static chap05.stream.Dish.menu;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import chap05.stream.Dish;
public class Reducing {
    // reducing : reduce
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
        // 요소의 합
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);
        int product = numbers.stream().reduce(1, (a, b) -> a * b);
        System.out.println(product);
        int sum2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum2);
        System.out.println("------------------------------------------");

        // 최댓값과 최솟값
        int max = numbers.stream().reduce(0, (a, b) -> Integer.max(a, b));
        System.out.println(max);
        Optional<Integer> max_optional = numbers.stream().reduce(Integer::max);
        max_optional.ifPresent(System.out::println);

        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        min.ifPresent(System.out::println);
        System.out.println("------------------------------------------");

        //map-reduce
        int count = menu.stream()
                .map(d -> 1)
                .reduce(0, (a, b) -> a + b);
        System.out.println(count);
        int calories = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println("Number of calories:" + calories);
        //stream count
        long count_stream = menu.stream().count();
        System.out.println("Stream count: " + count_stream);
    }
}
