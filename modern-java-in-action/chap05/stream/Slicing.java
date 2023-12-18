package chap05.stream;

import java.util.Arrays;
import java.util.List;

import static chap05.stream.Dish.menu;
import static java.util.stream.Collectors.toList;

public class Slicing {
    public static void main(String[] args) {
        // slicing : takeWhile, dropWhile
        // 칼로리 값 기준으로 오름차순 정렬
        List<Dish> specialMenu = Arrays.asList(
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER));
        System.out.println("Filtered sorted menu:");
        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .collect(toList());
        filteredMenu.forEach(System.out::println);
        System.out.println("------------------------------------------");

        // 프레디케이트를 이용한 슬라이싱 (takeWhile, dropWhile)
        // takeWhile
        System.out.println("Sorted menu sliced with takeWhile():");
        List<Dish> slicedMenu1 = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(toList());
        slicedMenu1.forEach(System.out::println);
        System.out.println("------------------------------------------");

        // dropWhile
        System.out.println("Sorted menu sliced with dropWhile():");
        List<Dish> slicedMenu2 = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(toList());
        slicedMenu2.forEach(System.out::println);
        System.out.println("------------------------------------------");


        // 스트림 축소 (limit)
        List<Dish> dishesLimit3 = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3)
                .collect(toList());
        System.out.println("Truncating a stream:");
        dishesLimit3.forEach(System.out::println);
        System.out.println("------------------------------------------");

        // 요소 건너뛰기 (skip)
        List<Dish> dishesSkip2 = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(toList());
        System.out.println("Skipping elements:");
        dishesSkip2.forEach(System.out::println);
    }
}
