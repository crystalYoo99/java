package chap05.transactionExample;

import java.util.*;
import java.util.stream.Collectors;

public class TransactionExample {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //1. 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
        List<Transaction> tr2011 = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(tr2011);
        System.out.println("------------------------------------------");

        //2. 거래자가 근무하는 모든 도시를 중복 없이 나열하시오.
        List<String> distinctCities = transactions.stream()
                .map(t-> t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(distinctCities);
        System.out.println("------------------------------------------");

        //3. 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
        List<Trader> cambridgeTrader = transactions.stream()
                .map(Transaction::getTrader)
                .filter(t -> t.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(cambridgeTrader);
        System.out.println("------------------------------------------");

        //4. 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.
        List<String> tradersName = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        String tradersNameAns = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
        System.out.println(tradersName);
        System.out.println(tradersNameAns);
        System.out.println("------------------------------------------");


        //5. 밀라노에 거래자가 있는가?
        boolean milan = transactions.stream()
                        .anyMatch(t -> Objects.equals(t.getTrader().getCity(), "Milan"));
        System.out.println(milan);
        System.out.println("------------------------------------------");

        //6. 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오.
        List<Integer> camTransaction = transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .collect(Collectors.toList());
        System.out.println(camTransaction);
        System.out.println("------------------------------------------");

        //7. 전체 트랜잭션 중 최댓값은 얼마인가?
        Optional<Integer> highest = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        highest.ifPresent(System.out::println);
        System.out.println("------------------------------------------");

        //8. 전체 트랜잭션 중 최솟값은 얼마인가?
        Optional<Integer> smallest = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);
        smallest.ifPresent(System.out::println);
        System.out.println("------------------------------------------");

    }
}
