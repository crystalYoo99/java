# Chapter 11. null 대신 Optional 클래스
> Keyword : null, Optional, NullPointerException

## 11.1 값이 없는 상황을 어떻게 처리할까?
### 11.1.1 보수적인 자세로 NullPointerException 줄이기
- null 확인 코드를 추가해서 NullPointerException을 줄이려함
- 깊은 의심 : 변수가 null인지 의심되어 중첩 if 블록 추가. 코드 들여쓰기 수준 증가.
### 11.1.2 null 때문에 발생하는 문제
에러의 근원 / 코드 어지럽힘 / 아무 의미 없음 / 자바 철학에 위배(포인터!) / 형식 시스템에 구멍 만듦(null은 무형식)
### 11.1.3 다른 언어는 null 대신 무얼 사용하나?
#### 안전 내비게이션 연산자 (?.) - 그루비 등
ex) def carInsuranceName = person?.car?.insurance?.name
#### Maybe -  하스켈 / Option[T] - 스칼라
주어진 형식의 값을 갖거나 아니면 아무 값도 갖지 않을 수 있다. 따라서 null 참조 개념은 자연스럽게 사라진다

## 11.2 Optional 클래스 소개
### java.util.Optional<T>
- Optional은 선택형값을 캡슐화하는 클래스
- 값이 있으면 Optional 클래스는 값을 감싼다. 
- 값이 없으면 Optional.empty 메서드로 Optional을 반환
#### null 참조와 Optional.empty()
null을 참조하려 하면 NullPointerException이 발생하지만 Optional.empty()는 Optional 객체

## 11.3 Optional 적용 패턴 (값 존재하면 / 값 없으면)
### 11.3.1 Optional 객체 만들기
#### 빈 Optional : empty
빈 Optional 인스턴스 반환
#### null이 아닌 값으로 Optional 만들기 : of
값을 감싸는 Optional을 반환하고, 값이 null이면 NullPointerException을 발생함
#### null값으로 Optional 만들기 : ofNullable 
값이 존재하면 값을 감싸는 Optional을 반환하고, 값이 null이면 빈 Optional을 반환함

### 11.3.2 맵으로 Optional의 값을 추출하고 변환하기
#### map 
값이 존재하면 제공된 매핑 함수를 적용함

### 11.3.3 flatMap으로 Optional 객체 연결
#### flatMap 
인수로 제공된 함수를 적용한 결과 Optional을 반환 / 빈 Optional을 반환

### 11.3.4 Optional 스트림 조작
#### stream
존재하는 값만 포함하는 스트림을 반환 / 빈 스트림을 반환

### 11.3.5 디폴트 액션과 Optional 언랩 
#### get
Optional이 감싸고 있는 값을 반환 / NoSuchElementException이 발생
#### or 
같은 Optional을 반환 / Supplier에서 만든 Optional을 반환
#### orElse
값을 반환 / 기본값을 반환
#### orElseGet
값을 반환 / Supplier에서 제공하는 값을 반환
#### orElseThrow 
값을 반환 / Supplier에서 생성한 예외를 발생
#### ifPresent 
지정된 Consumer를 실행 / 아무 일도 일어나지 않음
#### ifPresentOrElse 
지정된 Consumer를 실행 / 아무 일도 일어나지 않음

### 11.3.6 두 Optional 합치기
#### isPresent 
true를 반환하고 / false를 반환함
### 11.3.7 필터로 특정값 거르기
#### filter 
- 값이 존재하며 프레디케이트와 일치하면 값을 포함하는 Optional을 반환
- 값이 없거나 프레디케이트와 일치하지 않으면 빈 Optional을 반환함

## 11.4 Optional을 사용한 실용 예제
### 11.4.1 잠재적으로 null이 될 수 있는 대상을 Optional로 감싸기
- null을 반환하는 것보다는 Optional을 반환하는 것이 더 바람직
- if-then-else 사용하거나 / Optional.ofNullable 이용
ex) get 메서드의 반환값을 Optional로 감싸기
```java
Optional<Object> value = Optional.ofNullable(map.get("key"));
```
### 

### 11.4.2 예외와 Optional 클래스
- null을 반환하는 대신 예외를 발생시킬 때도 있다
- 예외를 발생시키는 메서드를 작은 유틸리티 메서드를 구현해서 Optional을 반환하게 하기
ex) Integer.parseInt(String)
```java
public static Optional<Integer> stringToInt(String s) {
    try {
        return Optional.of(Integer.parseInt(s));
    } catch (NumberFormatException e) {
        return Optional.empty();
    }
}
```

### 11.4.3 기본형 Optional을 사용하지 말아야 하는 이유
- Optional도 기본형으로 특화된 OptionalInt, OptionalLong, OptionalDouble 등의 클래스를 제공
- Optional의 최대 요소 수는 한 개이므로 기본형 특화 클래스로 성능을 개선 불가
- 기본형 특화 Optional은 map, flatMap 등 지원하지 않아서 사용 안하는게 좋음

## 11.5 마치며
- 역사적으로 프로그래밍 언어에서는 null 참조로 값이 없는 상황을 표현해왔다.
- 자바 8에서는 값이 있거나 없음을 표현할 수 있는 클래스 java.util.Optional<T>를 제공
- 팩토리 메서드 Optional.empty, Optional.of, Optional.ofNullable 등을 이용해서 Optional 객체를 만들 수 있다.
- Optional 클래스는 스트림과 비슷한 연산을 수행하는 map, flatMap, filter 등의 메서드를 제공
- Optional로 값이 없는 상황을 적절하게 처리하도록 강제할 수 있다. 즉, Optional로 예상치 못한 null 예외를 방지할 수 있다.
- Optional을 활용하면 더 좋은 API를 설계할 수 있다. 즉, 사용자는 메서드의 시그니처만 보고도 Optional값이 사용되거나 반환되는지 예측할 수 있다.
