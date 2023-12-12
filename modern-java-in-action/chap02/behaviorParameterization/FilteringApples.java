package chap02.behaviorParameterization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FilteringApples {
  public static void main(String... args) {
    List<Apple> inventory = Arrays.asList(
        new Apple(80, Color.GREEN),
        new Apple(155, Color.GREEN),
        new Apple(120, Color.RED)
    );

    List<Integer> numbers = Arrays.asList(
            5, 10, 100, 23
    );

    // 1. 녹색 사과 필터링
    //--> red 사과 필터링하려면 메서드를 또 만들어줘야 함.
    List<Apple> greenApples = filterGreenApples(inventory);

    // 2. 색을 파라미터화
    //--> 색 이외에 다른 요구사항 있으면 또 파라미터 추가해야 하고, 중복 코드 많아짐.
    List<Apple> greenApples2 = filterApplesByColor(inventory, Color.GREEN);
    List<Apple> redApples2 = filterApplesByColor(inventory, Color.RED);
    List<Apple> heavyApples2 = filterApplesByWeight(inventory, 150);

    // 3. 가능한 모든 속성으로 필터링
    //--> 모든 속성을 메서드 파라미터로 추가하면 유연하지 않음. 새 속성 추가되거나 여러 조건으로 필터링 불가.
    List<Apple> greenApples3 = filterApplesByConditions(inventory, Color.GREEN, 0, true);
    List<Apple> heavyApples3 = filterApplesByConditions(inventory, null, 150, false);

    // 4. 추상적 조건으로 필터링
    //--> 프레디케이트 객체로 사과 검사 조건 캡슐화
    List<Apple> greenApples4 = filterApples(inventory, new AppleGreenColorPredicate());
    List<Apple> heavyApples4 = filterApples(inventory, new AppleHeavyWeightPredicate());
    List<Apple> redAndHeavyApples = filterApples(inventory, new AppleRedAndHeavyPredicate());

    //quiz2-1. prettyFormatter
    prettyPrintApple(inventory, new AppleFancyFormatter());
    prettyPrintApple(inventory, new AppleSimpleFormatter());

    // 5. 익명 클래스 사용
    // --> 익명 클래스를 이용해서 ApplePredicate를 구현하는 객체를 만드는 방법
    List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
      public boolean test(Apple apple){
        return Color.RED.equals(apple.getColor());
      }
    });

    // 6. 람다 표현식 사용
    List<Apple> result = filterApples(inventory, (Apple apple) -> Color.RED.equals(apple.getColor()));

    // 7. 리스트 형식으로 추상화
    // --> 형식 파라미터 T를 사용해서 다양한 리스트에 필터 메서드 사용할 수 있게 됨.
    List<Apple> redApples7 = filter(inventory, (Apple apple) -> Color.RED.equals(apple.getColor()));
    List<Integer> evenNumbers = filter(numbers, (Integer i) -> i % 2 == 0);
  }

  //1. 첫번째 시도 : 녹색 사과 필터링 (java8 이전)
  public static List<Apple> filterGreenApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (Color.GREEN.equals(apple.getColor())) {
        result.add(apple);
      }
    } return result;
  }

  //2. 두번째 시도 : 색을 파라미터화
  public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (apple.getColor().equals(color)) {
        result.add(apple);
      }
    } return result;
  }
  public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple: inventory) {
      if ( apple.getWeight() > weight ) {
        result.add(apple);
      }
    }
    return result;
  }

  //3. 세번째 시도 : 가능한 모든 속성으로 필터링
  public static List<Apple> filterApplesByConditions(List<Apple> inventory, Color color,
                                         int weight, boolean flag) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple: inventory) {
      if ((flag && apple.getColor().equals(color)) ||
              (!flag && apple.getWeight() > weight)) {
        result.add(apple);
      }
    }
    return result;
  }

  //4. 네번째 시도 : 추상적 조건으로 필터링
  //프레디케이트 객체로 사과 검사 조건 캡슐화
  public static class AppleHeavyWeightPredicate implements ApplePredicate {
    public boolean test(Apple apple) {
      return apple.getWeight() > 150;
    }
  }

  public static class AppleGreenColorPredicate implements ApplePredicate {
    public boolean test(Apple apple) {
      return Color.GREEN.equals(apple.getColor());
    }
  }

  public static class AppleRedAndHeavyPredicate implements ApplePredicate {
    public boolean test(Apple apple) {
      return Color.RED.equals(apple.getColor())
              && apple.getWeight() > 150;
    }
  }

  public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
    List<Apple> result = new ArrayList<>();
    for(Apple apple: inventory) {
      if(p.test(apple)) {
        result.add(apple);
      }
    }
    return result;
  }

  //quiz 2-1. 유연한 prettyPrintApple 메서드 구현하기
  public static class AppleFancyFormatter implements AppleFormatter {
    public String accept(Apple apple) {
      String characteristic = apple.getWeight() > 150 ? "heavy" :
              "light";
      return "A " + characteristic +
              " " + apple.getColor() +" apple";
    }
  }
  public static class AppleSimpleFormatter implements AppleFormatter {
    public String accept(Apple apple) {
      return "An apple of " + apple.getWeight() + "g";
    }
  }
  public static void prettyPrintApple(List<Apple> inventory, AppleFormatter f) {
    for(Apple apple: inventory) {
      String output = f.accept(apple);
      System.out.println(output);
    }
  }

  //5. 다섯번째 시도 : 익명 클래스 사용

  //6. 여섯번째 시도 : 람다 표현식 사용

  //7. 일곱번째 시도 : 리스트 형식으로 추상화
  public interface Predicate<T> {
    boolean test(T t);
  }
  public static <T> List<T> filter(List<T> list, Predicate<T> p) {
    List<T> result = new ArrayList<>();
    for(T e: list) {
      if(p.test(e)) {
        result.add(e);
      }
    }
    return result;
  }
}
