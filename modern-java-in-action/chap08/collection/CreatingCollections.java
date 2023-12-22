package chap08.collection;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class CreatingCollections {
    public static void main(String[] args) {
        creatingLists();
        creatingSets();
        creatingMaps();
    }

    private static void creatingLists() {
        System.out.println("------ Creating Lists ------");

        System.out.println("--> Creating a list the old-fashioned way");
        List<String> friends = new ArrayList<>();
        friends.add("Raphael");
        friends.add("Olivia");
        friends.add("Thibaut");
        System.out.println(friends);

        System.out.println("--> Arrays.asList() 팩토리 메서드 사용");
        List<String> friends2 = Arrays.asList("Raphael", "Olivia");
        // 고정 크기의 리스트라 요소를 갱신할 순 있지만 새 요소를 추가하거나 요소 삭제는 불가
        friends2.set(0, "Richard");
        System.out.println(friends2);
        try {
            friends2.add("Thibaut");
            System.out.println("We shouldn't get here...");
        }
        catch (UnsupportedOperationException e) {
            System.out.println("As expected, we can't add items to a list created with Arrays.asList().");
        }

        System.out.println("--> Creating a Set from a List");
        Set<String> friends3 = new HashSet<>(Arrays.asList("Raphael", "Olivia", "Thibaut"));
        System.out.println(friends3);

        System.out.println("--> Creating a Set from a Stream");
        Set<String> friends4 = Stream.of("Raphael", "Olivia", "Thibaut").collect(Collectors.toSet());
        System.out.println(friends4);

        // List.of 팩토리 메소드로 리스트 만들기
        System.out.println("--> Creating a List with List.of()");
        List<String> friends5 = List.of("Raphael", "Olivia", "Thibaut");
        System.out.println(friends5);
        try {
            friends5.add("Chih-Chun");
            System.out.println("We shouldn't get here...");
        }
        catch (UnsupportedOperationException e) {
            System.out.println("As expected, we can't add items to a list created with List.of().");
        }
        try {
            friends5.set(1, "Chih-Chun");
            System.out.println("We shouldn't get here...");
        }
        catch (UnsupportedOperationException e) {
            System.out.println("Neither can we replace items in such a list.");
        }
    }

    private static void creatingSets() {
        System.out.println("------ Creating Sets ------");

        System.out.println("--> Creating a Set with Set.of()");
        Set<String> friends = Set.of("Raphael", "Olivia", "Thibaut");
        System.out.println(friends);

        // 중복된 요소를 제공해서 집합만들면 IllegalArgumentException 발생.
        System.out.println("--> Trying to pass duplicate items to Set.of()");
        try {
            Set<String> friends2 = Set.of("Raphael", "Olivia", "Olivia");
            System.out.println("We shouldn't get here...");
        }
        catch (IllegalArgumentException e) {
            System.out.println("As expected, duplicate items are not allowed with Set.of().");
        }
    }

    private static void creatingMaps() {
        System.out.println("--> Creating a Map with Map.of()");
        Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);
        System.out.println(ageOfFriends);

        System.out.println("--> Creating a Map with Map.ofEntries()");
        // Map.entry는 Map.Entry 객체를 만드는 새로운 팩토리 메서드
        // 10개 이상의 키와 값 쌍을 가진 맵에서는 Map.OfEntries 팩토리 메서드 이용
        // Map.Entry<K, V> 객체를 인수로 받으면 가변 인수로 구현됨
        Map<String, Integer> ageOfFriends2 = Map.ofEntries(
                entry("Raphael", 30),
                entry("Olivia", 25),
                entry("Thibaut", 26));
        System.out.println(ageOfFriends2);
    }
}
