# Chapter 13. 디폴트 메서드
> Keyword : 디폴트 메서드

## 13.1 변화하는 API
#### 인터페이스
인터페이스를 구현하는 클래스는 인터페이스에서 정의하는 모든 메서드 구현을 제공하거나 슈퍼클래스의 구현을 상속받아야 한다.

#### 자바8의 기본 구현 포함 인터페이스 정의 방법
1. 인터페이스 내부에 정적 메서드 사용
2. 디폴트 메서드 기능 사용 : 인터페이스 기본 구현 제공. 메서드 구현을 포함하는 인터페이스 제공.

### 디폴트 메서드 (default)
인터페이스의 기본 구현을 그대로 상속하므로 인터페이스에 자유롭게 새로운 메서드 추가 가능
```java
default void sort(Comparator<? super E> c) {
    Collections.sort(this,c);
}

default Stream<E> stream() {
    return StreamSupport.stream(spliterator(), false);
}
// stream 메서드는 내부적으로 StreamSupport.stream 메서드를 호출해서 스트림 반환
```