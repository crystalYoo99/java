# Chapter 6. 스트림으로 데이터 수집
> Keyword : Collectors 클래스, 리듀싱, 데이터 그룹화, 분할, 커스텀 컬렉터

## 6.1 컬렉터란 무엇인가?
컬렉터 : Stream.collect 메서드의 인수  
Collector 인터페이스 구현은 스트림의 요소를 어떤 식으로 도출할지 지정  
collect로 결과를 수집하는 과정을 간단하면서도 유연한 방식으로 정의할 수 있다  

### 고급 리듀싱 기능을 수행하는 컬렉터
collect에서는 리듀싱 연산을 이용해서 스트림의 각 요소를 방문하면서 컬렉터가 작업을 처리  
보통 함수를 요소로 변환할 때는 컬렉터를 적용하며 최종 결과를 저장하는 자료구조에 값을 누적한다.  
*Collector 인터페이스의 메서드를 어떻게 구현하느냐에 따라 스트림에 어떤 리듀싱 연산을 수행할지 결정*

### Collectors에서 제공하는 메서드의 기능
- 스트림 요소를 하나의 값으로 리듀스하고 요약 : 다양한 계산 수행에 활용
- 요소 그룹화 : 다수준으로 그룹화하거나 각각의 결과 서브그룹에 추가로 리듀싱 연산 적용
- 요소 분할 : 프레디케이트를 그룹화 함수로 상요

## 6.2 리듀싱과 요약
- 컬렉터로 스트림의 항목을 컬렉션으로 재구성할 수 있다. 
- 컬렉터로 스트림의 모든 항목을 하나의 결과로 합칠 수 있다.
### 6.2.1 스트림값에서 최댓값과 최솟값 검색 (Collectors.maxBy, Collectors.minBy)
```java
Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
Optional<Dish> mostCalorieDish = menu.stream().collect(maxBy(dishCaloriesComparator));
```
### 6.2.2 요약 연산 (Collectors.summingInt)
요약연산 : 스트림에 있는 객체의 숫자 필드의 합계나 평균 등을 반환하는 연산. 리듀싱 기능 자주 사용됨.
ex) summingInt, summingLong, summingDouble, averagingInt, summarizingInt
```java
int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
```

### 6.2.3 문자열 연결 (joining)
컬렉터에 joining 팩토리 메서드를 이용하면 스트림의 각 객체에 toString 메서드를 호출해서 추출한 모든 문자열을 하나의 문자열로 연결해서 반환
```java
String shortMenu = menu.stream().map(Dish::getName).collect(joining());

// Dish 클래스가 요리명을 반환하는 toString 메서드를 포함하고 있으면 map 생략 가능
String shortMenu = menu.stream().collect(joining());
// 구분 문자열
// String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));
```
### 6.2.4 범용 리듀싱 요약 연산 (Collectors.reducing)
위의 모든 컬렉터는 reducing 팩토리 메서드로도 구현 가능. 즉, 범용 Collectors.reducing으로도 구현 가능.
reducing(시작값 혹은 반환값, 변환 함수, 연산)
reducing(연산) --> 시작값은 스트림의 첫번째 요소, 항등함수(자신 그대로 반환)
```java
int totalCalories = menu.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
Optional<Dish> mostCalorieDish = menu.stream().collect(reducing(
        (d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
```

### collect와 reduce
- collect 메서드는 도출하려는 결과를 누적하는 컨테이너를 바꾸도록 설계된 메서드
- reduce는 두 값을 하나로 도출하는 불변형 연산
- 가변 컨테이너 관련 작업이면서 병렬성을 확보하려면 collect 메서드로 리듀싱 연산을 구현

## 6.3 그룹화 (Collectors.groupingBy)
```java
Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
```
### 6.3.1 그룹화된 요소 조작 (filtering, mapping, flatMapping)
Collectors 클래스는 일반적인 분류 함수에 Collector 형식의 두 번째 인수를 갖도록 groupingBy 팩토리 메서드를 오버로드
- filtering : 프레디케이트로 각 그룹의 요소와 필터링 된 요소를 재그룹화
- mapping : 맵핑 함수를 이용해 요소를 변환. 인수는 매핑 함수와 각 항목에 적용한 함수를 모으는 데 사용하는 또 다른 컬렉터.
- flatMapping :  두 수준의 리스트를 한 수준으로 평면화
```java
// filtering
Map<Dish.Type, List<Dish>> caloricDishesByType = menu.stream()
        .collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));

// mapping
Map<Dish.Type, List<String>> dishNamesByType = menu.stream()
        .collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));

// flatMapping
Map<String, List<String>> dishTags = new HashMap<>();
dishTags.put("pork", asList("greasy", "salty")); dishTags.put("beef", asList("salty", "roasted"));
// 각 형식의 요리의 태그 추출
Map<Dish.Type, Set<String>> dishNamesByType = menu.stream()
        .collect(groupingBy(Dish::getType, flatMapping(dish -> dishTags.get(dish.getName() ).stream(), toSet())));
```

### 6.3.2 다수준 그룹화
두 번째 groupingBy 컬렉터를 외부 컬렉터로 전달
```java
Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream().collect(
        groupingBy(Dish::getType, groupingBy(dish -> {
            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL; 
            else return CaloricLevel.FAT;
        })
        )
        );
```

### 6.3.3 서브그룹으로 데이터 수집
- groupingBy 컬렉터에 두 번째 인수로 다양한 컬렉터를 전달
- 분류 함수만 인수로 갖는 groupingBy(f) 는 groupingBy(f, toList())의 축약형
- *리듀싱 컬렉터는 절대 Optional.empty()를 반환하지 않음*
```java
Map<Dish.Type, Long> typesCount = menu.stream().collect(groupingBy(Dish::getType, counting()));
```

#### 컬렉터 결과를 다른 형식에 적용하기 (Collectors.collectingAndThen)
- 적용할 컬렉터와 변환 함수를 인수로 받아 다른 컬렉터를 반환.
- 반환되는 컬렉터는 기존 컬렉터의 래퍼 역할을 하며 collect의 마지막 과정에서 변환 함수로 자신이 반환하는 값을 매핑
```java
Map<Dish.Type, Dish> mostCaloricByType = menu.stream()
        .collect(groupingBy(Dish::getType, 
            collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional:;get)));
```

#### groupingBy와 함께 사용하는 다른 컬렉터
- 같은 그룹으로 분류된 모든 요소에 리듀싱 작업을 수행할 때는 groupingBy에 두 번째 인수로 전달한 컬렉터 사용
- mapping으로 만들어진 컬렉터도 groupingBy와 자주 사용

## 6.4 분할

