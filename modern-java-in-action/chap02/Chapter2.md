# Chapter 2. 동작 파라미터화 코드 전달하기
> Keyword : 동작 파라미터화, 프레디케이트, 익명 클래스, 람다 표현식, 형식 파라미터 T

- 실전 예제 : Comparator, Runnable, GUI

## 2.1 변화하는 요구사항에 대응하기
### 동작 파라미터화
**동작을 (한 조각의 코드로) 캡슐화한 다음에 메서드로 전달해서 메서드의 동작을 파라미터화**
아직은 어떻게 실행할 것인지 결정하지 않은 코드 블록. 나중에 프로그램에서 호출.  
즉, 코드 블록의 실행은 나중으로 미뤄짐. 나중에 실행될 메서드의 인수로 코드 블록을 전달할 수 있다.  
결과적으로 코드 블록에 따라 메서드의 동작이 파라미터화됨.
자주 바뀌는 요구사항에 효과적으로 대응할 수 있음.

### 람다표현식과 동작 파라미터화
동작 파라미터화를 추가하려면 쓸데없는 코드가 늘어나는데 자바 8은 람다 표현식으로 이 문제를 해결

### 유연한 코드 만들기
변화하는 요구사항에 유연하게 대응할 수 있게 코드 구현.  
**거의 비슷한 코드가 반복 존재하면 그 코드를 추상화한다.**
메서드에 파라미터를 계속 추가하는 방식의 깂 파라미터화는 유연하지 못함.

- 변화하는 요구사항을 파라미터화
- 동작 파라미터화를 통해 유연성을 얻을 수 있다.

DRY (Do not Repeat Yourself) : 같은 것을 반복하지 말 것

## 2.2 동작 파라미터화
### 프레디케이트
참 또는 거짓을 반환하는 함수.
선택 조건을 결정하는 인터페이스 정의하기.

### 전략 디자인 패턴 (strategy design pattern)
각 알고리즘(전략)을 캡슐화하는 알고리즘 패밀리를 정의해둔 다음 런타임에 알고리즘을 선택하는 기법
ex) ApplePredicate라는 알고리즘 패밀리가 사과 선택 전략을 캡슐화. 전략은 AppleGreenColorPredicate 등.

### predicate와 동작 파라미터화
**predicate 객체로 조건을 캡슐화**
메서드가 predicate 객체를 받아서 조건을 검사하도록 함.
전달한 객체에 의해 메서드의 동작이 결정. 즉, 메서드의 동작을 파라미터화한 것.
메서드가 다양한 동작(또는 전략)을 받아서 내부적으로 다양한 동작을 수행할 수 있다.
- 컬렉션 탐색 로직과 각 항목에 적용할 동작을 분리할 수 있다는 것이 강점
- 유연한 API를 만들 때 중요한 역할



## 2.3 복잡한 과정 간소화
### 동작 파라미터화의 방법 3가지
- 클래스 / 익명 클래스 / 람다 (뒤로 갈수록 간결함)

### 2.3.1 익명 클래스
자바의 지역 클래스(블록 내부에 선언된 클래스)와 비슷한 개념. 이름이 없는 클래스.  
익명 클래스를 이용하면 **클래스 선언과 인스턴스화를 동시에 수행**할 수 있다. 즉석에서 필요한 구현을 만들어서 사용할 수 있다.
익명 클래스를 이용하면 코드의 양을 줄일 수 있지만 람다 표현식으로 더 가독성 있는 코드 구현 가능.  

GUI 애플리케이션에서 이벤트핸들러 객체 구현할 때 익명 클래스 종종 사용.  

### 2.3.2 람다 표현식
```java
List<Apple> result = filterApples(inventory, (Apple apple) → RED.equals(apple.getColor()));
```

### 2.3.3 리스트 형식으로 추상화
형식 파라미터 T
리스트 형식으로 추상화하면 사과, 바나나, 정수, 문자열 등 다양한 리스트에 필터 메서드 사용 가능.


## 2.4 실전 예제
### 2.4.1 Comparator로 정렬하기
java.util.Comparator 객체로 sort 메서드의 동작을 파라미터화
Comparator를 구현해서 sort 메서드의 동작을 다양화할 수 있다.
```java
// java.util.Comparator
public interface Comparator<T> {
    int compare(T o1, T o2);
}
```
```java
// 무게가 적은 순서로 정렬하는 예제
// 익명 클래스 이용
inventory.sort(new Comparator<Apple>() {
    public int compare(Apple a1, Apple a2) {
        return a1.getWeight().compareTo(a2.getWeight());
    }
});

//람다식 이용
inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
```

### 2.4.2 Runnable로 코드 블록 실행하기
Runnable 인터페이스를 이용해서 실행할 코드 블록을 지정할 수 있다.
Runnable을 이용해서 다양한 동작을 스레드로 실행할 수 있다.

자바 스레드를 이용하면 병렬로 코드 블록을 실행할 수 있다.
자바 8까지는 Thread 생성자에 객체만 전달할 수 있었다.
그래서 결과를 반환하지 않는 void run 메소드를 포함하는 익명 클래스가 Runnable 인터페이스를 구현하도록 하는 것이 일반적인 방법이었다.

```java
// java.lang.Runnable
public interface Runnable {
    void run();
}
```
```java
// 익명 클래스 이용
Thread t = new Thread(new Runnable() {
    public void run() {
        System.out.println(“Hello world”);
    }
});

// 람다식 이용
Thread t = new Thread(() -> System.out.println("Hello world"));
```

### 2.4.3 Callable을 결과로 반환하기
ExecutorService 인터페이스 : 태스크 제출과 실행 과정의 연관성을 끊어준다. 
태스크를 스레드 풀로 보내고 결과를 Future로 저장할 수 있다는 점이 스레드와 Runnable을 이용하는 방식과는 다르다.  

Callable 인터페이스를 이용해서 결과를 반환하는 태스크를 만든다. 이 방식은 Runnable의 업그레이드 버전이라고도 볼 수 있다.
```java
// java.util.concurrent.Callable 
public interface Callable<V> { 
    V call();
}
```
```java
// 태스크를 실행하는 스레드의 이름을 반환하는 예제
ExecutorService executorService = Executors.newCachedThreadPool();
Future<String> threadName = executorService.submit(new Callable<String>() {
    @Override public String call() throws Exception {
        return Thread.currentThread().getName();
    }
});

Future<String> threadName = executorService.submit(() -> Thread.currentThread().getName());
```

### 2.4.4 GUI 이벤트 처리하기
EventHandler는 setOnAction 메서드의 동작을 파라미터화
자바FX에서는 setOnAction 메서드에 EventHandler를 전달해서 이벤트에 어떻게 반응할지 설정할 수 있다.

```java
Button button = new Button("Send");
button.setOnAction(new EventHandler<ActionEvent>() {
    public void handle(ActionEvent event) {
        label.setText("Sent!!");
    }
});

button.setOnAction((ActionEvent event) → label.setText(“Sent!!”));
```

## 2.5 마치며
- 동작 파라미터화에서는 메서드 내부적으로 다양한 동작을 수행할 수 있도록 코드를 메서드 인수로 전달
- 동작 파라미터화를 이용하면 변화하는 요구사항에 더 잘 대응할 수 있는 코드를 구현할 수 있으며 나중에 엔지니어링 비용을 줄일 수 있다.
- 코드 전달 기법을 이용하면 동작을 메서드의 인수로 전달할 수 있다. 하지만 자바8 이전에는 코드를 지저분하게 구현해야 했다. 익명 클래스로도 어느 정도 코드를 깔끔하게 만들 수 있지만 자바 8에서는 인터페이스를 상속받아 여러 클래스를 구현해야 하는 수고를 없앨 수 있는 방법을 제공한다.
- 자바 API의 많은 메서드는 정렬, 스레드, GUI 처리 등을 포함한 다양한 동작으로 파라미터화할 수 있다.