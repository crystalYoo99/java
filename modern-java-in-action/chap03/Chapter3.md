# Chapter 3. 람다 표현식
> Keyword : 람다, 실행 어라운드 패턴, 함수형 인터페이스, 함수 디스크립터, 형식 추론, 메서드 참조

## 3.1 람다란 무엇인가?
**(parameters) -> expression**  
**(parameters) -> {statement;}**  
람다표현식은 파라미터, 화살표, 바디로 이루어진다.  

- 익명 : 보통의 메서드와 달리 이름이 없다
- 함수 : 메서드와 달리 특정 클래스에 종속되지 않음. 하지만 메서드처럼 파라미터 리스트, 바디, 반환형식, 가능한 예외리스트를 포함.
- 전달 : 메서드 인수로 전달하거나 변수로 저장할 수 있다
- 간결성 : 익명 클래스보다 간결한 코드 구현

## 3.2 어디에, 어떻게 람다를 사용할까?
### 3.2.1 함수형 인터페이스
**정확히 하나의 추상 메서드를 지정하는 인터페이스**  
@FunctionalInterface 어노테이션은 함수형 인터페이스임을 가리키는 어노테이션  
ex) Predicate<T> 는 함수형 인터페이스. filter 메서드를 파라미터화할 수 있다.  
ex) 자바 API의 함수형 인터페이스 예시 :  Comparator, Runnable, etc.  

람다 표현식으로 함수형 인터페이스의 추상 메서드 구현을 직접 전달할 수 있어서
전체 표현식을 함수형 인터페이스의 인스턴스(정확히는 함수형 인터페이스를 구현한 클래스의 인스턴스)로 취급할 수 있다.

### 3.2.2 함수 디스크립터
람다 표현식의 시그니처를 서술하는 메서드  
ex) () -> void : 파라미터 리스트가 없고 void를 반환하는 함수  

함수형 인터페이스의 추상 메서드 시그니처는 람다 표현식의 시그니처를 가리킨다.  
**람다 표현식은 변수에 할당하거나 함수형 인터페이스를 인수로 받는 메서드로 전달할 수 있으며,   
함수형 인터페이스의 추상 메서드와 같은 시그니처를 갖는다.**


## 3.3 람다 활용:실행 어라운드 패턴
**어떤 메소드의 동작을 파라미터화할지 정함 --> 시그니처와 일치하는 함수형 I 만들기 --> 정의한 함수형 I를 메소드 인수로 전달 
--> 함수형 I의 추상 메소드와 시그니처가 일치하는 람다 전달 가능 --> 람다로 다양한 동작(함수형 I의 추상 M 구현)을 직접 전달**

### 실행 어라운드 패턴
초기화/준비 코드 - 작업 - 정리/마무리 코드  
실제 자원을 처리하는 코드를 설정(자원 열기)과 정리(자원 닫기) 두 과정이 둘러싸는 형태.
setup과 cleanup 과정은 대부분 비슷.
** try-with-resources 구문 사용하면 자원 명시적으로 닫을 필요 없음  

### 3.3.1 1단계 : 동작 파라미터화를 기억하라
**기존의 설정, 정리 과정은 재사용**하고 processFile 메서드만 다른 동작을 수행하도록 명령. ***processFile의 동작을 파라미터화.***  
```java
// BufferedReader에서 두행을 출력하는 코드  
String result = processFile((BufferedReader br) → br.readLine() + br.readLine());  
```

### 3.3.2 2단계 : 함수형 인터페이스를 이용해서 동작 전달
함수형 인터페이스 자리에 람다 사용.  
BufferedReader → String 과 IOException을 throw 할 수 있는 시그니처와 일치하는 함수형 인터페이스 만들기.  
정의한 인터페이스를 메서드의 인수로 전달.  

### 3.3.3 3단계 : 동작 실행
시그니처와 일치하는 람다 전달 가능.  
**람다 표현식으로 함수형 인터페이스의 추상 메서드 구현을 직접 전달할 수 있으며 전달된 코드는 함수형 인터페이스의 인스턴스로 전달된 코드와 같은 방식으로 처리.**  
—> 따라서 인터페이스를 인수로 전달받은 메서드의 바디 내에서 함수형 인터페이스 객체의 추상 메서드를 호출할 수 있다.

### 3.3.4 4단계 : 람다 전달
람다로 다양한 동작을 메서드로 전달


## 3.4 함수형 인터페이스 사용
### java.util.function 패키지
다양한 람다 표현식을 사용하려면 공통의 함수 디스크립터를 기술하는 함수형 인터페이스 집합 필요

### 3.4.1 Predicate<T>
test() : T -> boolean
- 불리언 표현식
```java
Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
```

### 3.4.2 Consumer<T>
accept() : T -> void
- 동작수행 (객체에서 소비)
```java
// forEach와 람다를 이용해서 리스트의 모든 항목을 출력
public <T> void forEach(List<T> list, Consumer<T> c) {
    for(T t: list) c.accept(t);
}
forEach(Arrays.asList(1,2,3,4,5), (Integer i) -> System.out.println(i));
```

### 3.4.3 Function<T>
apply() : T -> R
- 입력을 출력으로 매핑 (객체에서 선택/추출)
```java
// String 리스트를 인수로 받아 각 String의 길이를 포함하는 Integer 리스트로 변환하는 map 메서드
public <T, R> List<R> map(List<T> list, Function<T, R> f) {
    List<R> result = new ArrayList<>();
    for(T t: list) result.add(f.apply(t));
    return result;
}

List<Integer> l = map(Arrays.asList("lambdas", "in", "action"), (String s) -> s.length());
```

### 3.4.4 기본형 특화
#### 박싱, 언박싱, 오토박싱
박싱 : 기본형 -> 참조형 / 언박싱 : 참조형 -> 기본형 / 오토박싱 : 자동 변환

#### 특화된 형식의 함수형 인터페이스
- 제네릭 파라미터 T에는 참조형만 가능
- 변환과정에서 비용 소모 : 박싱한 값은 기본형을 감싸는 래퍼이고 힙에 저장. 메모리 더 소비.
- IntPredicate : 박싱 x / Predicate<Integer> : 박싱 o, Integer객체로 박싱

### 그 외 함수형 인터페이스
Supplier<T> - get(), () -> T, 객체 생성
Callable<T> : call(), () -> T
BinaryOperator<T> : (T, T) -> T, 두 값 조합


## 3.5 형식 검사, 형식 추론, 제약
람다로 함수형 I의 인스턴스를 만들 수 있다.  
근데 람다 표현식 자체에는 람다가 어떤 함수형 인터페이스를 구현하는지 정보가 없다.  
람다의 실제 형식 파악이 필요하다.  

### 3.5.1 형식 검사
람다가 사용되는 콘텍스트로 람다의 형식 추론
- 람다가 사용된 콘텍스트 파악
- 대상 형식 확인 ex) Predicate<Apple>, Callable<Integer>
- 함수형 인터페이스의 추상 메서드 확인
- 함수 디스크립터와 람다의 시그니쳐가 일치하는지 확인. 일치하면 완료!

#### 콘텍스트
람다가 전달될 메서드 파라미터. 람다가 할당되는 변수 등

### 3.5.2 같은 람다, 다른 함수형 인터페이스
#### 대상형식
어떤 콘텍스트에서 기대되는 람다 표현식의 형식
대상 형식이라는 특징 때문에 같은 람다식이라도 호환되는 추상 메서드 가진 다른 함수형 인터페이스로 사용가능.
#### 다이아몬드 연산자
<>로 콘텍스트에 따른 제네릭 형식 추론 가능
주어진 클래스 인스턴스 표현식을 두 개 이상의 다양한 콘텍스트에 사용할 수 있다.  
이때 인스턴스 표현식의 형식 인수는 콘텍스트에 의해 추론
```java
List<String> listOfStrings = new ArrayList<>();
List<Integer> listOfIntegers = new ArrayList<>();
```
#### 특별한 void 호환 규칙
람다 바디에 일반 표현식이 있으면 void 반환하는 함수 디스크립터와 호환된다.
```java
// Consumer는 T -> void / 람다식은 T -> boolean
Consumer<String> b = s -> list.add(s);
```
#### 람다 캐스트
**람다 표현식의 콘텍스트는 함수형 인터페이스**
어떤 메소드의 시그니처가 사용되어야 하는지 명시적으로 구분가능
```java
execute((Action)() -> {});
```

### 3.5.3 형식 추론
자바 컴파일러는 람다 표현식이 사용된 콘텍스트(대상 형식)으로 함수형 I 추론
대상형식으로 함수 디스크립터 알 수 있음 -> 람다 시그니쳐로 추론 가능 -> 파라미터 형식 생략 가능
```java
Comparator<Apple> c = (a1, a2) -> {};
```

### 3.5.4 지역 변수 사용
#### 람다 캡처링
익명함수처럼 자유변수를 활용 가능.
자유변수란 파라미터로 넘겨진 변수가 아닌 외부에서 정의된 변수.
단, **지역 변수는 final이나 유사 final만 가능** (한번만 할당)

#### 지역 변수의 젱갸
**인스턴스 변수는 힙에 저장되고 지역변수는 스택에 저장됨**  
람다가 스레드에서 실행되면 변수 할당한 스레드가 사라져도 람다 실행 스레드에서 접근하려할 수 있다.  
따라서 자유 지역 변수의 복사본을 제공한다. 복사본 값 바뀌면 안되기 대문에 지역변수에 값 한번만 할당.  

#### 클로저 (closure)
함수의 비지역변수를 자유롭게 참조할 수 있는 함수의 인스턴스.  
클로저는 외부에 정의된 변수의 값에 접근하고 값을 바꿀 수 있다.
람다와 익명 클래스는 클로저처럼 메서드 인수로 전달이 가능하고, 외부 영역 변수에 접근이 가능하다.
단, 람다가 정의된 메서드의 지역변수 값은 바꿀 수 없다.

## 3.6 메서드 참조
**클래스::메소드명**
- 실제로 메서드 호출이 아니므로 괄호는 없음
ex) (Apple a) -> a.getWeight() / Apple::getWeight

### 메서드 참조의 3가지 유형
- 정적 메소드 참조 
- 다양한 형식의 인스턴스 메서드 참조 - 람다 표현식의 파라미터로 전달
- 기존 객체의 인스턴스 메서드 참조 - 람다 표현식에서 현존하는 외부 객체의 메서드 호출
```java
Integer::parseInt // 정적 메소드 참조
String::length // 다양한 형식의 인스턴스 메서드 참조
expensiveTransaction::getValue // 기존 객체의 인스턴스 메서드 참조
```

### 람다 표현식을 메서드 참조로 줄여서 표현하는 단축 규칙
메서드 참조는 콘텍스트 형식과 일치!
1. (args) -> ClassName.staticMethod(args) / ClassName::staticMethod
2. (args0, rest) -> arg0.instanceMethod(rest) /  ClassName::instanceMethod
3. (args) -> expr.instanceMethod(args) / expr::instanceMethod

### 생성자 참조
ClassName::new 로 기존 생성자의 참조 만들 수 있다.
```java
Supplier<Apple> c1 = Apple::new; //() -> new Apple();
Apple a1 = c1.get();
```

## 3.7 람다, 메서드 참조 활용하기
목표 : inventory.sort(comparing(Apple::getWeight));
**동작 파라미터화 -> 익명 클래스 -> 람다표현식 (함수 디스크립터) -> 메서드 참조**
### 3.7.1 1단계 : 코드 전달
- sort메서드 시그니쳐 파악 : void sort(Comparator<? Super E> c)
- 인수로 가는 Comparator 객체에 동작 포함해서 동작  파라미터화
- Comparator 인터페이스 구현하는 AppleComparator  만들기

### 3.7.2 2단계 : 익명 클래스 사용
- 한번 사용할 Comparator는 익명으로 구현하는 게 더 낫다

### 3.7.3 3단계 : 람다 표현식 사용
- 함수형 인터페이스를 기대하는 곳에 람다 표현식 사용 가능
- Comparator의 함수 디스크립터는 (T, T) -> int / 우리는 (Apple, Apple) -> int
- comparing 메서드 사용 : Comparable 키 추출해서 Comparator 객체로 만드는 Function 함수를 인수로 받는 정적 메서드

### 3.7.4 4단계 : 메서드 참조 사용


## 3.8 람다 표현식을 조합할 수 있는 유용한 메서드
### 3.8.1 Comparator 조합
Comparator.comparing 으로 비교에 사용할 키 추출하는 Function 기반의 Comparator 반환 가능
```java
Comparator<Apple> c = Comparator.comparing(Apple::getWeight);
```
#### 역정렬 - reverse
주어진 비교자 순서 뒤바꾸는 디폴트 메서드
#### Comparator 연결 - thenComparing
두번째 Comparator. 첫 비교자로 같으면 두번째 비교자.
```java
inventory.sort(comparing(Apple::getWeight).reversed()
        .thenComparing(Apple::getCountry));
```

### 3.8.2 Predicate 조합
#### negate
특정 프레디케이트 반전
#### and, or
왼쪽에서 오른쪽으로 연결
```java
Predicate<Apple> notRedApple = redApple.negate();
```


### 3.8.3 Function 조합
#### andThen
주어진 함수 먼저 적용한 결과를 다른 함수 입력으로.
#### compose
인수로 주어진 함수 먼저 실행 후 결과를 외부함수 인수로 제공
```java
Function<Integer, Integer> h = f.andThen(g); //g(f(x))
Function<Integer, Integer> h = f.compose(g); //f(g(x))
```