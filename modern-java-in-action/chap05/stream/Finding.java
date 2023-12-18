package chap05.stream;

import static chap05.stream.Dish.menu;
import chap05.stream.Dish;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;
public class Finding {
    // finding : allMatch, anyMatch, noneMatch, findFirst, findAny
    public static void main(String... args) {
        // 프레디케이트가 적어도 한 요소와 일치하는지 확인 (anyMatch)
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("Finding Vegetarian friendly");
        }

        // 프레디케이트가 모든 요소와 일치하는지 검사 (allMatch, noneMatch)
        // allMatch
        boolean isHealthy = menu.stream().allMatch(dish -> dish.getCalories() < 1000);
        System.out.println(isHealthy);
        System.out.println(isHealthyMenu());
        // noneMatch
        System.out.println(menu.stream().noneMatch(d -> d.getCalories() >= 1000));

        // 요소 검색 (findAny)
        Optional<Dish> dish = menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();
        Optional<Dish> dish2 = findVegetarianDish();
        dish.ifPresent(d -> System.out.println(d.getName()));

        // 첫번째 요소 찾기 (findFirst)
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree =
                someNumbers.stream()
                        .map(n -> n * n)
                        .filter(n -> n%3 == 0)
                        .findFirst(); //9
        System.out.println(firstSquareDivisibleByThree);
    }

    private static boolean isHealthyMenu() {
        return menu.stream().allMatch(d -> d.getCalories() < 1000);
    }

    private static Optional<Dish> findVegetarianDish() {
        return menu.stream().filter(Dish::isVegetarian).findAny();
    }
}
