package chap05.stream;
import static java.util.stream.Collectors.toList;
import static chap05.stream.Dish.menu;
import chap05.stream.Dish;
import java.util.Arrays;
import java.util.List;

public class Filtering {
    // filtering : filter
    public static void main(String[] args) {
        // 프레디케이트로 필터링
        System.out.println("Filtering with a predicate");
        List<Dish> vegetarianMenu = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(toList());
        vegetarianMenu.forEach(System.out::println);
        System.out.println("------------------------------------------")
        ;
        // 고유 요소로 필터링
        System.out.println("Filtering unique elements:");
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
    }
}
