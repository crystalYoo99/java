# Chapter 5. 스트림 활용
> Keyword : 필터링, 슬라이싱, 매칭, 검색, 매핑, 리듀싱, Optional, 무한 스트림

## 5.1 필터링 (filter, distinct)
프레디케이트로 필터링 (filter 메서드) / 고유요소 필터링 (distinct 메서드)
```java
// 프레드케이트로 필터링 (filter)
List<Dish> vegeterianMenu = menu.stream().filter(Dish::isVegeterian).collect(toList());

// 고유요소 필터링 (distinct)
List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
numbers.stream().filter(i -> i%2 == 0).distinct().forEach(System.out::println);
```

## 5.2 스트림 슬라이싱 (takeWhile, dropWhile, limit, skip)
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

## 5.3 매핑 (map, flatMap)
### 5.3.1 스트림의 각 요소에 함수 적용하기 (map 메서드)
함수를 인수로 받음. 각 요소에 함수가 적용되며 결과가 새로운 요소로 매핑됨.
```java
List<Integer> dishNameLengths = menu.stream().map(Dish::getName).map(String::length).collect(toList());
```

### 5.3.2 스트림 평면화 (map + Arrays.stream, flatMap 메서드)
["Hello", "World"] --> ["H", "e", "l", "o", "W", "r", "d"]
#### 1) map과 Arrays.stream --> 불가
Arrays.stream 메서드 : 문자열 받아서 스트림 만듦.
```java
words.stream().map(word -> sord.split("")).map(Arrays::stream).distinct().collect(toList());
// --> 스트림 리스트 (List<Stream<String>>)나온다. 불가.
// 먼저 각 단어를 개별 문자열로 이뤄진 배열로 만든 후 각 배열을 별도의 스트림으로 해야 한다.
```

#### 2) flatMap 메서드
스트림의 각 값을 다른 스트림으로 만든 후 모든 스트림을 하나의 스트림으로 연결.  
각 배열을 스트림이 아니라 스트림의 콘텐츠로 매핑. 하나의 평면화된 스트림을 반환.
```java
List<String> uniqueCharacters = word.stream().map(word -> word.split("")).flatMap(Arrays::stream).distinct().collect(toList());
// Stream<String> -> Stream<String[]> (map에서) -> Stream<String> (flatMap에서) -> List<String> (collect에서)
```


## 5.4 검색과 매칭 (anyMatch, allMatch, noneMatch, findAny)
### 5.4.1 프레디케이트가 적어도 한 요소와 일치하는지 확인 (anyMatch 메서드)
anyMatch(<Predicate>) : 불리언 반환하는 최종 연산

### 5.4.2 프레디케이트가 모든 요소와 일치하는지 검사 (allMatch, noneMatch 메서드) --> 서로 반대 연산
allMatch, noneMatch는 서로 반대 연산을 수행
#### 쇼트서킷 평가
anyMatch, allMatch, noneMatch, findFirst, findAny, limit 등은 스트림 쇼트서킷 기법  
즉, 자바의 &&, || 같은 연산 활용. 전체 스트림을 처리하지 않아도 결과 반환. 원하는 요소 찾으면 즉시 반환.

### 5.4.3 요소 검색 (findAny 메서드)
현재 스트림에서 임의의 요소 반환
#### Optional 이란?
- Optional<T> 클래스 (java.util.Optional)
- 값의 존재나 부재 여부를 표현하는 컨테이너 클래스
- Optional로 null 확인 관련 버그 피함. 값이 존재하는지 확인하고, 값이 없을 때 어떻게 처리할지 강제하는 기능 제공

#### Optional 기능
- isPresent() : Optional이 값 포함하면 true. 포함 안하면 false.
- isPresent(Consumer<T> block) : 값 있으면 주어진 블록 실행. Consumer 함수형 인터페이스에는 T 형식의 인수를 받으로 void 반환하는 람다 전달 가능.
- T get() : 값 존재하면 값 반환. 없으면 NoSuchElementException 발생.
- T orElse(T other) : 값 있으면 값 반환. 없으면 기본값 반환.
```java
menu.stream().filter(Dish::isVegeterian).findAny().ifPresent(dish -> System.out.println(dish.getName()));
//findAny()가 Optional<Dish> 반환. 값 있으면 출력하고 없으면 아무 일도 일어나지 않음.
```


### 5.4.4 첫번째 요소 찾기 (findFirst 메서드)
findAny와 findFirst는 병렬성 때문에 둘 다 존재한다.  
반환 순서 상관 없으면 병렬스트림에서는 제약 적은 findAny를 사용한다. 

```java
boolean isAnyVegeterian = menu.stream().anyMatch(Dish::isVegeterian)
boolean isHealthy = menu.stream().allMatch(dish -> dish.getCalories() < 1000);
Optional<Dish> dish = menu.stream().filter(Dish::isVegeterian).findAny();
```

## 5.5 리듀싱 (reduce)
- 리듀싱 연산 : 모든 스트림 요소 처리해서 값으로 도출.
- 폴드 (fold) : 함수형 프로그래밍 언어 용어로는 종이(스트림)을 작은 조각이 될 때까지 반복해서 접은 것과 비슷해서 폴드라 부름. 
### 5.5.1 요소의 합
- 초깃값 0 / 초깃값 없으면 Optional 객체 반환
- 두 요소 조합해서 새 값 만드는 BinaryOperator<T>  
-> reduce 연산을 사용하면 스트림이 하나의 값으로 줄어들 때까지 람다는 각 요소 반복 조합

### 5.5.2 최댓값과 최솟값
- 초깃값
- 스트림의 두 요소를 합쳐서 하나의 값으로 만드는 데 사용할 람다

```java
// 요소의 합
int sum = numbers.stream().reduce(0, (a, b) -> a+b);
int sum2 = numbers.stream().reduce(0, Integer::sum);
// 최댓값과 최솟값
Optional<Integer> min = numbers.stream.reduce(Integer::min);
Optional<Integer> min2 = numbers.stream.reduce((x, y) -> x < y? x: y);
```
### 5.5.3 리듀스 메서드와 병렬화
reduce 사용하면 내부 반복이 추상화되면서 병렬로 reduce 실행 가능.  
기존 가변 누적자 패턴은 병렬화와 거리가 너무 멀다.

### 5.5.4 맵 리듀스 (map-reduce) 패턴
map과 reduce를 연결하는 기법. 쉽게 병렬화되는 특징으로 구글이 웹 검색에 적용.


#### 스트림 연산 : 상태 없음과 상태 있음.
- 내부 상태를 갖지 않는 연산 (stateless operation) : map, filter 등은 입력 스트림에서 각 요소 받아 0 또는 결과를 출력스트림으로 보냄. 
- 내부 상태를 갖는 연산 (stateful operation) : sorted, distinct 같은 연산은 다르다. 정렬하거나 중복제거하려면 과거 이력을 알아야 함.
- ex) 무한소수스트림을 역순으로 만들면... 연산 수행 필요한 저장소 크기 정해지지 않는다. -> 내부 상태 가진다.

## 5.6 실전 연습

## 5.7 숫자형 스트림
### 5.7.1 기본형 특화 스트림
스트림 API는 박싱 비용 피할 수 있게 IntStream, DoubleStream, LongStream  제공.  
각 인터페이스는 sum, max 처럼 자주 사용하는 숫자 관련 리듀싱 연산 수행 메서드 제공.  
객체 스트림으로 복원도 가능. 특화스트림은 오직 박싱과정에서 일어나는 효율성과 관련있고, 스트림에 추가 기능 제공하진 않음.  
#### 1) 숫자 스트림으로 매핑 (mapToInt, mapToDouble, mapToLong 메서드)
- 스트림 -> 특화스트림
- map과 같은 기능, but! Stream<T> 대신 특화된 스트림 반환
- max, min, sum, average 등 다양한 유틸리티 메서드 사용 가능
#### 2) 객체스트림으로 복원 (boxed 메서드)
- 특화스트림 -> 일반스트림
- IntStream의 map 연산은 'int 인수 int 반환 람다'를 인수로 받아서 정수 아닌 다른 값 반환하고 싶으면 다시 일반 스트림으로 변환 필요
#### 3) 기본값 OptionalInt
- Optional : 값 존재 여부 가리킬 수 있는 컨테이너 클래스
- Integer, String 등 참조 형식으로 파라미터화 가능. OptionalInt 등 특화스트림 버전도 제공.
```java
// 숫자 스트림으로 매핑 (mapToInt)
// IntStream 반환. Stream<Integer> X!
int calories = menu.stream().mapToInt(Dish::getCalories).sum();

// 기본값 OptionalInt
OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max();
int max = maxCalories.orElse(1);
```

### 5.7.2 숫자범위 (range, rangeClosed 메서드)
- IntStream, LongStream이 제공
- 첫번째 인수 : 시작값 / 두번째 인수 : 종료값
- range : 시작, 종료값 결과에 포함x / rangeClosed : 포함ㅇ
```java
IntStream evenNumbers = IntStream.rangeClosed(1, 100).filter(n -> n%2 == 0);
```


## 5.8 스트림 만들기
기존에는 stream 메서드로 컬렉션에서 스트림 얻거나 범위의 숫자에서 스트림 만들었다. 다른 방법도 존재.
### 5.8.1 값으로 스트림 만들기 (Stream.of)
임의의 수를 인수로 받는 정적 메서드
Stream.empty() : 스트림 비움
```java
Stream<String> stream = Stream.of("Modern ", "Java ", "In ", "Action");
map(String::toUpperCase).forEach(System.out::println);

Stream<String> emptyStream = Stream.empty();
```

### 5.8.2 null이 될 수 있는 객체로 스트림 만들기 (Stream.ofNullable)
null이 될 수 있는 객체를 스트림(객체가 null이라면 빈 스트림)으로 만들어야 할 때 사용
```java
// 기존  System.getProperty는 제공된 키에 대응하는 속성 없으면 null 반환. 
// 이런 메소드를 스트림에 활용하려면 명시적으로 null 확인
String homeValue = System.getProperty("home");
Stream<String> homeValueStream = homeValue == null? Stream.empty(): Stream.of(value);

// Stream.ofNullable 사용
Stream<String> homeValueStreamNullable = Stream.ofNullable(System.getProperty("home"));

// null이 될 수 있는 객체를 포함하는 스트림값을 flatMap과 함께 사용하는 상황에서 유용하게 사용
Stream<String> values = Stream.of("config", "home", "user")
        .flatMap(key -> Stream.ofNullable(System.getProoperty(key)));
```

### 5.8.3 배열로 스트림 만들기 (Arrays.stream)
```java
int[] numbers = {2, 3, 5, 7, 11, 13};
int sum = Arrays.stream(numbers).sum();
```

### 5.8.4 파일로 스트림 만들기
- 파일을 처리하는 등 I/O 연산에 사용하는 자바의 NIO API (비블록 I/O)도 스트림 API 활용 가능
- Java.nio.file.Files의 많은 정적 메서드가 스트림 반환.
- 스트림의 소스가 I/O자원이라 메소드를 try/cat 블록으로 감싸고, 메모리 누수 막기 위해 자원 닫기
- 기존에는 Finally 블록에서 자원 닫았는데 Stream 인터페이스는 AutoCloseable 인터페이스를 구현. 따라서 try 블록 내의 자원 자동으로 관리됨.
```java
long uniqueWords = 0;
try(Stream<String> lines = File.lines(Paths.get("data.txt"), Charset.defaultCharset())){
        uniqueWords=lines.flatMap(line->Arrays.stream(line.split(" ")))
        .distinct()
        .count();
        }
catch(IOException e) {
    
        }
```

### 5.8.5 함수로 무한 스트림 만들기 (Stream.iterate, Stream.generate)
- 무한 스트림, 즉 크기가 고정되지 않은 스트림 만들기 가능
- 언바운드 스트림 : 요청할 때마다 주어진 함수 이용해서 값 만들기 때문에 무제한으로 값 계산 가능. 그래도 보통 limit(n)함수와 함께 사용

#### 1) iterate 메서드
- iterate(초깃값, 프레디케이트(언제까지 작업할지), 람다
- 연속된 일련의 값 만들 때 주로 사용.
- filter로는 중단 불가. 스트림 쇼트서킷 지원하는 takeWhile로.
```java
Stream.iterate(0, n -> n + 2).limit(10).forEach(System.out::println);
IntStream.iterate(0, n -> n < 100, n -> n+4).forEach(System.out::println);
IntStream.iterate(0, n -> n + 4).takeWhile(n -> n < 100).forEach(System.out::println);
```
#### 2) generate 메서드
- generate(Supplier<T>)
- iterate와 달리 생산된 각 값을 연속적으로 계산하지 않음.
```java
Stream.generate(Math::random).limit(5).forEach(System.out::println);
IntStream ones = IntStream.generate(() -> 1);
```


## 5.9 마치며
- 스트림 API를 이용하면 복잡한 데이터 처리 질의를 표현할 수 있다.
- filter, distinct, takeWhile (자바 9), dropWhile (자바 9), skip, limit 메서드로 스트림을 필터링하거나 자를 수 있다.
- 소스가 정렬되어 있다는 사실을 알고 있을 때 takeWhile과 dropWhile 메소드를 효과적으로 사용할 수 있다.
- map, flatMap 메서드로 스트림의 요소를 추출하거나 변환할 수 있다.
- findFirst, findAny 메서드로 스트림의 요소를 검색할 수 있다. allMatch, noneMatch, anyMatch 메서드를 이용해서 주어진 프레디케이트와 일치하는 요소를 스트림에서 검색할 수 있다.
- 이들 메서드는 쇼트서킷 short-circuit, 즉 결과를 찾는 즉시 반환하며, 전체 스트림을 처리하지는 않는다.
- Reduce 메서드로 스트림의 모든 요소를 반복 조합하며 값을 도출할 수 있다. 예를 들어 reduce로 스트림의 최댓값이나 모든 요소의 합계를 계산할 수 있다.
- filter, map 등은 상태를 저장하지 않는 상태 없는 연산이다. reduce 같은 연산은 값을 계산하는 데 필요한 상태를 저장한다. sorted, distinct 등의 메서드는 새로운 스트림을 반환하기에 앞서 스트림의 모든 요소를 버퍼에 저장해야 한다. 이런 메서드를 상태 있는 연산이라고 부른다.
- IntStream, DoubleStream, LongStream은 기본형 특화 스트림. 이들 연산은 각각의 기본형에 맞게 특화되어 있다.
- 컬렉션 뿐 아니라 값, 배열, 파일, iterate와 generate 같은 메서드로도 스트림을 만들 수 있다.
- 무한한 개수의 요소를 가진 스트림을 무한스트림
