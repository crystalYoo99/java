# Chapter 8. 컬렉션 API 개선
> Keyword : 컬렉션 API
- 컬렉션 팩토리 사용하기
- 리스트 및 집합과 사용할 새로운 관용 패턴 배우기
- 맵과 사용할 새로운 관용 패턴 배우기

## 8.1 컬렉션 팩토리
데이터 처리 형식을 설정하거나 데이터 변환할 필요 없으면 팩토리 메서드를 사용하는 게 편함.
### List.of / Set.of / Map.of 팩토리 메소드
1) 리스트 팩토리 (List.of) : add()나 set() 메서드로 추가, 변경 불가. 변경할 수 없는 리스트 만들어짐. null 요소 금지.
2) 집합 팩토리 (Set.of)
3) 맵 팩토리 (Map.of) : 열 개 이상의 맵에서는 Map.ofEntries 팩토리 메서드 이용. 키와 값을 감쌀 추가 객체 할당 필요.

### 오버로딩 vs 가변 인수
- List.of, Set.of, Map.of는 다양한 오버로드 버전이 있음  
- 내부적으로 가변 인수 버전은 추가 배열을 할당해서 리스트로 감싼다. 배열을 할당하고 초기화하고 가비지컬렉션하는 비용 지불.  
- 고정된 숫자의 요소(최대 10개까지)를 API로 정의해서 이런 비용 제거.  
- 열개 이상이면 가변 인수를 이용하는 메소드 사용됨.
```java
static <E> List<E> of(E e1, E e2, E e3, E e4)
static <E> List<E> of(E e1. E e2, E e3, E e4, E e5)

// static <E> List<E> of(E... elements) 
// 가변 인수 버전 아님. 다중요소를 받을 수 있게 만들지 않음.
```

## 8.2 리스트와 집합 처리
### removeIf() / replaceAll() / sort()
새 결과를 만드는 스트림 동작과 달리 기존 컬렉션을 바꾼다.  
컬렉션 객체를 Iterator 객체와 혼용하면 반복과 컬렉션 변경이 동시에 이뤄져서 쉽게 문제를 일으킴. 이 때 사용!
1) removeIf()
- 프레디케이트 만족하는 요소 제거
- List나 Set을 구현하거나 그 구현을 상속받은 모든 클래스에서 이용 가능
2) replaceAll ()
- 리스트에서 이용할 수 있는 기능. UnaryOperator 함수로 요소를 바꿈.
3) sort ()
- List 인터페이스에서 제공하는 기능. 리스트 정렬.

## 8.3 맵 처리
### 8.3.1 forEach ()
BiConsumer(키와 값을 인수로 받음)를 인수로 받는 메서드.

### 8.3.2 정렬 메서드 (Entry.comparingByValue / Entry.comparingByKey)
```java
Map<String, String> favoriteMovies = Map.ofEntries(entry("Raphael", "Star Wars"), entry("Cristina", "Matrix"));
favoriteMovies.entrySet().stream().sorted(Entry.comparingByKey()).forEachOrdered(System.out::println);
// Cristina=Matrix\n Raphael=Star Wars
```
#### HashMap 성능
- HashMap 내부 구조 바꿔서 성능 개선. 
- 기존의 맵의 항목은 키로 생성한 해시코드로 접근할 수 있는 버켓에 저장.
- 많은 키가 같은 해시코드를 반환하는 상황이 되면 O(n)의 시간이 걸리는 LinkedList로 버킷을 반환해야 하므로 성능이 저하
- 버킷이 너무 커질 경우 이를 O(log(n))의 정렬된 트리를 이용해 동적으로 치환해 충돌이 일어나는 요소 반환 성능을 개선
- 단, 키가 Comparable 형태여야 정렬된 트리 지원

### 8.3.3 getOrDefault()
첫 번째 인수로 키를, 두 번째 인수로 기본값을 받으며 맵에 키가 존재하지 않으면 두 번째 인수로 받은 기본값을 반환

### 8.3.4 계산 패턴 (computeIfAbsent, computeIfPresent, compute)
1) computeIfAbsent : 키에 해당하는 값 없거나 null이면 키를 이용해서 새 값을 계산하고 맵에 추가
2) computeIfPresent : 키가 존재하면 새 값 계산하고 맵에 추가
3) compute : 제공된 키로 새 값 계산하고 맵에 저장

### 8.3.5 삭제 패턴 (remove)
키가 특정한 값과 연관되었을 때만 항목을 제거하는 오버로드 버전 메서드 제공

### 8.3.6 교체 패턴 (replaceAll, Replace)
1) replaceAll : BiFunction을 적용한 결과로 각 항목의 값을 교체. List의 replaceAll과 비슷한 동작
2) Replace : 키가 존재하면 맵의 값 바꿈. 키가 특정 값으로 매핑되엇을 때만 값 교체하는 오버로드 버전도 있음.

### 8.3.7 합침 (merge)
중복된 키를 어떻게 합칠지 결정하는 BiFunction을 인수로 받음. putAll보다 유연하게 값 합칠 수 있음.
```java
// forEach와 merge 메서드로 충돌 해결
Map<String, String> everyone = new HashMap<>(family);
// 중복된 키 있으면 두 값 연결
friends.forEach((k, v) -> everyone.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));
System.out.println(everyone);

// 초기화 검사 구현
moviesToCount.merge(movieName, 1L, (key, count) -> count + 1L);
// 키의 반환값이 null이라 처음에는 1 사용됨. 그 다음부터는 BiFunction으로 값 증가.
```

## 8.4 개선된 ConcurrentHashMap
- 동시성 친화적이며 최신 기술을 반영한 HashMap 버전
- 내부 자료구조의 특정 부분만 잠궈 동시 추가, 갱신 작업을 허용
- 동기화된 Hashtable 버전에 비해 읽기 쓰기 연산 성능이 월등. 표준 HashMap은 비동기로 동작.
### 8.4.1 리듀스와 검색 (forEach, reduce, search)
1) forEach : 각  (키, 값) 쌍에 주어진 액션을 실행
2) reduce : 모든 (키, 값) 쌍을 제공된 리듀스 함수를 이용해 결과로 합침
3) search : 널이 아닌 값을 반환할 때까지 각 (키, 값) 쌍에 함수를 적용

#### 네 가지 연산 형태
1) 키, 값으로 연산(forEach, reduce, search)
2) 키로 연산(forEachKey, reduceKeys, searchKeys)
3) 값으로 연산(forEachValue, reduceValues, searchValues)
4) Map.Entry 객체로 연산(forEachEntry, reduceEntries, searchEntries)
- 이 연산들은  ConcurrentHashMap의 상태를 잠그지 않고 연산을 수행한다
- 연산에 병렬성 기준값(threshold)을 지정
- int, long, double 등 기본값에는 박싱 작업 필요 없는 전용 each reduce 연산 제공
```java
// ConcurrentHashMap은 여러 키와 값을 포함하도록 갱신됨
ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
long parallelismThreshold = 1;
Optional<Integer> maxValue = Optional.ofNullable(map.reduceValues(parallelismThreshold, Long::max));
```

### 8.4.2 계수 (mappingCount)
- 맵의 매핑 개수를 반환(int를 반환). 
- 매핑의 개수가 int 범위 넘어서는 이후의 상황을 대처할 수 있어서 size보다 나음

### 8.4.3 집합뷰 (keySet, newKeySet)
keySet() : ConcurrentHashMap을 집합 뷰로 반환
newKeySet() : ConcurrentHashMap으로 유지되는 집합

## 8.5 마치며
- 자바 9는 적의 원소를 포함하며 바꿀 수 없는 리스트, 집합, 맵을 쉽게 만들 수 있도록 List.of, Set.of, Map.of, Map.ofEntries 등의 컬렉션 팩토리를 지원
- 이들 컬렉션 팩토리가 반환한 객체는 만들어진 다음 바꿀 수 없다. 
- List 인터페이스는 removeIf, replaceAll, sort 세 가지 디폴트 메서드를 지원한다. 
- Set 인터페이스는 removeIf 디폴드 메서드를 지원한다. 
- Map 인터페이스는 자주 사용하는 패턴과 버그를 방지할 수 있도록 다양한 디폴트 메서드를 지원한다.
- ConcurrentHashMap은 Map에서 상속받은 새 디폴트 메서드를 지원함과 동시에 스레드 안전성도 제공한다