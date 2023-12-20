package chap06.toListCollector;

import chap05.stream.Dish;
import static chap05.stream.Dish.menu;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DishExample {
    public static void main(String args[]) {
        Stream<Dish> menuStream = menu.stream();
        List<Dish> dishes = menuStream.collect(new ToListCollector<Dish>());
        // 기존 : List<Dish> dishes = menuStream.collect(toList());

        List<Dish> dishes2 = menuStream.collect(
                ArrayList::new, //발행
                List::add, // 누적
                List::addAll); // 합침

        System.out.println(dishes);
    }

}
