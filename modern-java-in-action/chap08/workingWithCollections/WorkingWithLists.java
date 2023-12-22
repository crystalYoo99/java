package chap08.workingWithCollections;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class WorkingWithLists {
    public static void workingWithLists() {
        System.out.println("------ Working with lists ------");

        // 스트림 사용
        System.out.println("--> Stream으로 리스트의 아이템을 바꾸기");
        List<String> referenceCodes = Arrays.asList("a12", "C14", "b13");
        referenceCodes.stream()
                .map(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1))
                .collect(Collectors.toList())
                .forEach(System.out::println);
        System.out.println("... but the original List remains unchanged: " + referenceCodes);

        System.out.println("--> ListIterator로 리스트의 각 요소를 새로운 요소로 바꾸기");
        referenceCodes = Arrays.asList("a12", "C14", "b13");
        for (ListIterator<String> iterator = referenceCodes.listIterator(); iterator.hasNext(); ) {
            String code = iterator.next();
            iterator.set(Character.toUpperCase(code.charAt(0)) + code.substring(1));
        }
        System.out.println("This time it's been changed: " + referenceCodes);

        System.out.println("--> 팩토리 메서드 replaceAll()로 리스트의 각 요소를 새로운 요소로 바꾸기");
        referenceCodes = Arrays.asList("a12", "C14", "b13");
        System.out.println("Back to the original: " + referenceCodes);
        referenceCodes.replaceAll(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1));
        System.out.println("Changed by replaceAll(): " + referenceCodes);
    }
}
