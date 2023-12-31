# Chapter 4. 스트림 소개
> Keyword : 스트림, 컬렉션, 내부반복, 중간연산, 최종연산, 게으른 연산

## 4.1 스트림이란 무엇인가?
- 선언형으로 컬렉션 데이터 처리 가능. loop, if 조건문 등 제어블록 없이 선언형으로 필터링 등 수행 가능.
- filter, sorted, map, collect 등 여러 빌딩 블록 연산을 연결해서 복잡한 데이터 처리 파이프라인을 만들 수 있다.
- 고수준 빌딩 블록 : filter, sorted 등 연산은 고수준 빌딩 블록으로 이뤄져 있어서 특정 스레딩 모델에 제한 없이 사용가능

### 스트림 API 특징
- 선언형 : 간결. 가독성 좋아짐
- 조립가능 : 유연성 좋아짐
- 병렬화 : 성능 좋아짐

## 4.2 스트림 시작하기
### 스트림
데이터 처리 연산을 지원하도록 소스에서 추출된 연속된 요소
- 연속된 요소 : 컬렉션처럼 스트림은 특정 요소 형식으로 이뤄진 연속된 값 집합의 인터페이스를 제공. 컬렉션(자료구조)의 주제는 데이터, 스트림의 주제는 계산
- 소스 : 스트림은 컬렉션, 배열, I/O 자원 등 데이터 제공 소스로부터 데이터 소비. 순서 유지.
- 데이터 처리 연산 : filter, map  등으로 데이터 조작. 순차적 또는 병렬로 연산 실행 가능.

### 스트림 중요 특징
- 파이프라이닝 : 대부분의 스트림 연산은 연산끼리 연결해서 커다란 파이프라인 구성할 수 있게 스트림 자신을 반환.덕분에 게으름. 쇼트서킷 같은 최적화
- 내부 반복 : 반복자로 명시적으로 반복하는 컬렉션 / 스트림은 내부 반복 지원

### 데이터 처리 연산
- filter : 람다를 인수로 받아 스트림에서 특정 요소 제외
- map : 람다로 한 요소를 다른 요소로 변환하거나 정보 추출
- limit : 정해진 개수까지만 스트림에 저장되도록 스트림 크기 축소
- collect : 스트림을 다른 형식으로 변환. 다양한 변환 방법을 인수로 받아 특정 결과로 변환.
```java
filter(d -> d.getCalories() > 300) //Stream<Dish>
map(Dish::get.name) //Stream<String>
limit(3) //Stream<String>
collect(toList()) //List<String>
```

## 4.3 스트림과 컬렉션
### 공통점과 차이점
#### 공통점
연속된(sequenced) 요소 형식의 값을 저장하는 자료구조의 인터페이스를 제공
#### 차이점
1. 데이터 계산 시점 : 컬렉션과 스트림의 가장 큰 차이.
    - 컬렉션 (적극적 생성) : 생산자 중심. 팔기도 전에 창고 가득채우는 것 같음. 컬렉션은 현재 자료구조가 포함하는 모든 값을 메모리에 저장하는 자료구조. (like DVD) 컬렉션의 모든 요소는 컬렉션에 추가하기 전에 계산. 컬렉션에 요소를 추가하고 삭제하는 연산마다 컬렉션의 모든 요소를 메모리에 저장해야 하고 추가하려는 요소는 미리 계산되어야 함.
    - 스트림 (게으른 생성) : 소비자 중심. 요청할 때만 요청할 때만 요소 계산. like 인터넷 스트리밍. 요소 추가, 제거 x.
2. 딱 한번만 탐색 가능 : 탐색된 스트림의 요소는 소비됨. 반복자처럼 재탐색하려면 초기 데이터소스에서 새 스트림 만들어야 한다. -> if 데이터 소스가 I/O 채널이면? 반복 사용 불가 -> 새 스트림 불가.
3. 외부 반복과 내부 반복
   - 외부반복(컬렉션) : for-each 등으로 사용자가 직접 요소 반복. 명시적으로 컬렉션 항목을 하나씩 가져와서 처리한다. 병렬성 관리도 스스로 해야 해서 어렵다.
   - 내부반복(스트림) : 스트림 라이브러리는 반복을 알아서 처리하고 결과 어딘가에 저장한다. 반복자 필요 없음. 병렬성 구현 자동 선택. 작업을 투명하게 병렬로 처리하거나 더 최적화된 다양한 순서로 처리 가능.  
EX) '모든 장난감을 상자에 담자'는 컬렉션은 불가. 장난감 있어? 담아.를 반복.

## 4.4 스트림 연산
### 4.4.1 중간연산
다른 스트림 반환. 연결 가능. *단말 연산을 스트림 파이프라인에 실행하기 전까지 아무 연산도 수행하지 않음. 게으르다.*  
중간 연산을 합친 다음에 합쳐진 중간연산을 최종 연산으로 한번에 처리!  
filter (Predicate<T>), map (Function<T, R>), limit, sorted (Comparator<T>), distinct  

#### 스트림의 게으른 특성 때문에 얻는 최적화 효과
1. Limit 연산, 쇼트서킷
2. 루프 퓨전 : 서로 다른 연산이지만 한 과정으로 병합

### 4.4.2 최종연산
결과 도출. 스트림 이외의 결과 반환.  
forEach (void), count (long(generic)), collect

### 4.4.3 스트림 이용하기
1. 질의 수행할 (컬렉션 같은) 데이터 소스
2. 스트림 파이프라인을 구성할 중간 연산 연결
3. 스트림 파이프라인을 실행하고 결과를 만들 최종 연산

#### 스트림과 빌더 패턴
스트림 파이프라인 개념은 빌더 패턴과 비슷.  
빌더 패턴은 호출을 연결해서 설정 만듦. 스트림에서 중간 연산 연결하는 것처럼.  
그리고 준비된 설정에 build 메서드 호출. 스트림에서 최종연산처럼.  

## 4.5 로드맵

## 4.6 마치며
- 스트림은 소스에서 추출된 연속 요소로, 데이터 처리 연산을 지원한다.
- 스트림은 내부 반복을 지원한다. 내부 반복은 filter, map, sorted 등의 연산으로 반복을 추상화한다.
- 스트림에는 중간 연산과 최종 연산이 있다.
- 중간 연산은 filter와 map처럼 스트림을 반환하면서 다른 연산과 연결되는 연산. 중간 연산을 이용해서 파이프라인을 구성할 수 있지만 어떤 결과도 생성할 수 없다
- forEach나 count 처럼 스트림 파이프라인을 처리해서 스트림이 아닌 결과를 반환하는 연산을 최종 연산이라고 한다.
- 스트림의 요소는 요청할 때 게으르게 계산된다.