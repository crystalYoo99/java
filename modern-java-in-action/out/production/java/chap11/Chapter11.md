# Chapter 11. null 대신 Optional 클래스
> Keyword : null, Optional, NullPointerException

## 11.1 값이 없는 상황을 어떻게 처리할까?
### 11.1.1 보수적인 자세로 NullPointerException 줄이기
null 확인 코드를 추가해서 NullPointerException을 줄이려함
### 11.1.2 null 때문에 발생하는 문제
### 11.1.3 다른 언어는 null 대신 무얼 사용하나?

## 11.2 Optional 클래스 소개

## 11.3 Optional 적용 패턴
### 11.3.1 Optional 객체 만들기
### 11.3.2 맵으로 Optional의 값을 추출하고 변환하기
### 11.3.3 flatMap으로 Optional 객체 연결
### 11.3.4 Optional 스트림 조작
### 11.3.5 디폴트 액션과 Optional 언랩
### 11.3.6 두 Optional 합치기
### 11.3.7 필터로 특정값 거르기
empty 빈 Optional 인스턴스 반환
filter 값이 존재하며 프레디케이트와 일치하면 값을 포함하는 Optional을 반환하고, 값이 없거나
프레디케이트와 일치하지 않으면 빈 Optional을 반환함
flatMap 값이 존재하면 인수로 제공된 함수를 적용한 결과 Optional을 반환하고, 값이 없으면 빈
Optional을 반환함
get 값이 존재하면 Optional이 감싸고 있는 값을 반환하고, 값이 없으면
NoSuchElementException이 발생함
ifPresent 값이 존재하면 지정된 Consumer를 실행하고, 값이 없으면 아무 일도 일어나지 않음
ifPresentOrElse 값이 존재하면 지정된 Consumer를 실행하고, 값이 없으면 아무 일도 일어나지 않음
isPresent 값이 존재하면 true를 반환하고, 값이 없으면 false를 반환함
map 값이 존재하면 제공된 매핑 함수를 적용함
of 값이 존재하면 값을 감싸는 Optional을 반환하고, 값이 null이면 NullPointerException
을 발생함
ofNullable 값이 존재하면 값을 감싸는 Optional을 반환하고, 값이 null이면 빈 Optional을 반환함
or 값이 존재하면 같은 Optional을 반환하고, 값이 없으면 Supplier에서 만든 Optional을
반환
orEle 값이 존재하면 값을 반환하고, 값이 없으면 기본값을 반환함
orElseGet 값이 존재하면 값을 반환하고, 값이 없으면 Supplier에서 제공하는 값을 반환함
orElseThrow 값이 존재하면 값을 반환하고, 값이 없으면 Supplier에서 생성한 예외를 발생함
stream 값이 존재하면 존재하는 값만 포함하는 스트림을 반환하고, 값이 없으면 빈 스트림을 반환