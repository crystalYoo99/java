# Chapter 5. 스트림 활용
> Keyword : 필터링, 슬라이싱, 매칭, 검색, 매핑, 리듀싱, 무한 스트림

## 5.1 필터링
프레디케이트로 필터링 (filter 메서드) / 고유요소 필터링 (distinct 메서드)
```java
// 프레드케이트로 필터링 (filter)
List<Dish> vegeterianMenu = menu.stream().filter(Dish::isVegeterian).collect(toList());

// 고유요소 필터링 (distinct)
List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
numbers.stream().filter(i -> i%2 == 0).distinct().forEach(System.out::println);
```

## 5.2 스트림 슬라이싱
### 5.2.1 프레디케이트를 이용한 슬라이싱 (takeWhile, dropWhile)
#### takeWhile
모든 스트림에 프레디케이트 적용해 스트림 슬라이스 가능
#### dropWhile
나머지 요소 선택. 프레디케이트가 처음으로 거짓이 되는 시점까지 발견된 요소 버림.  
프레디케이트 거짓되면 그 지점에서 직접 중단하고 남은 모든 요소 반환.  

### 5.2.2 스트림 축소 (limit(n) 메서드)
프레디케이트와 일치하는 처음 n 요소를 선택 후 즉시 반환.

### 5.2.3 요소 건너뛰기 (skip(n) 메서드)
처음 n개 요소 제외한 스트림 반환. limit(n)과 상호보완적 연산 수행.
```java
// 프레디케이트를 이용한 슬라이싱 - takeWhile
List<Dish> slicedMenu = specialMenu.stream().takeWhile(dish -> dish.getCalories() < 320).collect(toList());

// 스트림축소 - limit
List<Dish> slicedMenu3 = specialMenu.stream().filter(dish -> dish.getCalories() < 320).limit(3).collect(toList());
```

