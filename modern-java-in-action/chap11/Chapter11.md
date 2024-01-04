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
## 11.3 Optional 적용 패턴
### 11.3.1 Optional 객체 만들기
#### 빈 Optional : empty
빈 Optional 인스턴스 반환
#### null이 아닌 값으로 Optional 만들기 : of
값이 존재하면 값을 감싸는 Optional을 반환하고, 값이 null이면 NullPointerException을 발생함
#### null값으로 Optional 만들기 : ofNullable 
값이 존재하면 값을 감싸는 Optional을 반환하고, 값이 null이면 빈 Optional을 반환함

### 11.3.2 맵으로 Optional의 값을 추출하고 변환하기
#### map 
값이 존재하면 제공된 매핑 함수를 적용함

### 11.3.3 flatMap으로 Optional 객체 연결
#### flatMap 
값이 존재하면 인수로 제공된 함수를 적용한 결과 Optional을 반환하고, 값이 없으면 빈 Optional을 반환함

### 11.3.4 Optional 스트림 조작
#### stream
값이 존재하면 존재하는 값만 포함하는 스트림을 반환하고, 값이 없으면 빈 스트림을 반환

### 11.3.5 디폴트 액션과 Optional 언랩
#### get
- 값이 존재하면 Optional이 감싸고 있는 값을 반환
- 값이 없으면 NoSuchElementException이 발생
#### or 
값이 존재하면 같은 Optional을 반환하고, 값이 없으면 Supplier에서 만든 Optional을 반환
#### orElse
- 값이 존재하면 값을 반환하고, 값이 없으면 기본값을 반환함
#### orElseGet
- 값이 존재하면 값을 반환하고, 값이 없으면 Supplier에서 제공하는 값을 반환함
#### orElseThrow 
- 값이 존재하면 값을 반환하고, 값이 없으면 Supplier에서 생성한 예외를 발생함
#### ifPresent 
- 값이 존재하면 지정된 Consumer를 실행하고, 값이 없으면 아무 일도 일어나지 않음
#### ifPresentOrElse 
- 값이 존재하면 지정된 Consumer를 실행하고, 값이 없으면 아무 일도 일어나지 않음

### 11.3.6 두 Optional 합치기
#### isPresent 
값이 존재하면 true를 반환하고, 값이 없으면 false를 반환함
### 11.3.7 필터로 특정값 거르기
#### filter 
- 값이 존재하며 프레디케이트와 일치하면 값을 포함하는 Optional을 반환
- 값이 없거나 프레디케이트와 일치하지 않으면 빈 Optional을 반환함




