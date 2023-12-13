package chap03.lambdaNMethodReference;

import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Sorting {

  public static void main(String... args) {
    List<Apple> inventory = new ArrayList<>();
    inventory.addAll(Arrays.asList(
        new Apple(80, Color.GREEN),
        new Apple(155, Color.GREEN),
        new Apple(120, Color.RED)
    ));

    // 1. 코드 전달
    inventory.sort(new AppleComparator());
    System.out.println(inventory);
    inventory.set(1, new Apple(30, Color.GREEN));

    // 2. 익명 클래스 사용
    inventory.sort(new Comparator<Apple>() {
      @Override
      public int compare(Apple a1, Apple a2) {
        return a1.getWeight() - a2.getWeight();
      }
    });
    System.out.println(inventory);
    inventory.set(1, new Apple(20, Color.RED));

    // 3. 람다 표현식 사용
    inventory.sort((a1, a2) -> a1.getWeight() - a2.getWeight());
    System.out.println(inventory);
    inventory.set(1, new Apple(10, Color.RED));

    // 4. 메서드 참조 사용
    inventory.sort(comparing(Apple::getWeight));
    System.out.println(inventory);
  }

  static class AppleComparator implements Comparator<Apple> {
    @Override
    public int compare(Apple a1, Apple a2) {
      return a1.getWeight() - a2.getWeight();
    }
  }
}
