package chap01.methodReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FilteringApples {
  public static void main(String... args) {
    List<Apple> inventory = Arrays.asList(
        new Apple(80, "green"),
        new Apple(155, "green"),
        new Apple(120, "red")
    );

    // 메서드 참조
    // [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
    List<Apple> greenApples = filterApples(inventory, FilteringApples::isGreenApple);
    System.out.println(greenApples);

    // [Apple{color='green', weight=155}]
    List<Apple> heavyApples = filterApples(inventory, FilteringApples::isHeavyApple);
    System.out.println(heavyApples);

    //람다
    // [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
    List<Apple> greenApples2 = filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));
    System.out.println(greenApples2);

    // [Apple{color='green', weight=155}]
    List<Apple> heavyApples2 = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
    System.out.println(heavyApples2);

    // []
    List<Apple> weirdApples = filterApples(inventory, (Apple a) -> a.getWeight() < 80 || "brown".equals(a.getColor()));
    System.out.println(weirdApples);
  }

  //java8 이전
  public static List<Apple> filterGreenApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if ("green".equals(apple.getColor())) {
        result.add(apple);
      }
    } return result;
  }
  public static List<Apple> filterHeavyApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (apple.getWeight() > 150) {
        result.add(apple);
      }
    } return result;
  }

  // 자바8 - filter 메서드를 중복으로 구현하지 않음.
  // 메서드 참조에 사용. 람다 사용하면 필요 없음.
  public static boolean isGreenApple(Apple apple) {
    return "green".equals(apple.getColor());
  }

  public static boolean isHeavyApple(Apple apple) {
    return apple.getWeight() > 150;
  }

  public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (p.test(apple)) {
        result.add(apple);
      }
    }
    return result;
  }
}
