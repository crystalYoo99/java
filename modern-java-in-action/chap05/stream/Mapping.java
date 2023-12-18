package chap05.stream;

import static java.util.stream.Collectors.toList;
import static chap05.stream.Dish.menu;
import chap05.stream.Dish;

import java.util.Arrays;
import java.util.List;

public class Mapping {
    public static void main(String[] args) {
        // mapping : map, flatMap
        // map
        List<String> dishNames = menu.stream()
                .map(Dish::getName)
                .collect(toList());
        System.out.println(dishNames);

        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
        List<Integer> wordLengths = words.stream()
                .map(String::length)
                .collect(toList());
        System.out.println(wordLengths);
        System.out.println("------------------------------------------");

        // flatMap
        List<String> uniqueCharacters =
            words.stream()
                    .flatMap((String line) -> Arrays.stream(line.split("")))
                    .distinct()
                    .collect(toList());
        uniqueCharacters.forEach(System.out::println);

        List<Integer> numbers1 = Arrays.asList(1,2,3);
        List<Integer> numbers2 = Arrays.asList(3,4);
        List<int[]> pairs = numbers1.stream()
                .flatMap((Integer i) -> numbers2.stream()
                        .map((Integer j) -> new int[]{i, j})
                )
                .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                .collect(toList());
        pairs.forEach(pair -> System.out.printf("(%d, %d)", pair[0], pair[1]));
    }
}
