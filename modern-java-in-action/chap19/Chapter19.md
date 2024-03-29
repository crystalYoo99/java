# Chapter 19. 함수형 프로그래밍 기법
> Keyword : 일급시민, 고차원 함수, 커링, 부분 적용, 영속 자료구조, 패턴 매칭, 참조 투명성과 캐싱

## 19.1 함수는 모든 곳에 존재한다
#### 일급 함수
일반값처럼 취급할 수 있는 함수
### 19.1.1 고차원 함수
- 다음 중 하나 이상의 동작을 수행하는 함수 : 하나 이상의 함수를 인수로 받음 / 함수를 결과로 반환
- ex. Comparator.comparing : 함수를 인수로 받아서 다른 함수로 반환하는 정적 메서드
### 19.1.2 커링
- 함수를 모듈화하고 코드를 재사용하는 데 도움을 주는 기법
- 커링은 x와 y라는 두 인수를 받는 함수 f를 한 개의 인수를 받는 g라는 함수로 대체하는 기법
- ex. 커링을 활용해서 한 개의 인수를 갖는 변환 함수를 생산하는 ‘팩토리factory’를 정의

## 19.2 영속 자료구조
- 영속 : 저장된 값이 다른 누군가에 의해 영향을 받지 않는 상태 
- 함수형 메서드에서는 전역 자료구조나 인수로 전달된 구조를 갱신할 수 없다
- 자료구조를 바꾼다면 같은 메서드를 두 번 호출했을 때 결과가 달라지면서 참조 투명성에 위배되고 인수를 결과로 단순하게 매핑할 수 있는 능력이 상실되기 때문
### 19.2.1 파괴적인 갱신과 함수형
- 자료구조를 바꾸면서 생기는 버그를 함수형에서는 이 같은 부작용을 수반하는 메서드를 제한하는 방식으로 문제를 해결
- 계산결과를 표현할 자료구조가 필요하면 기존의 자료구조를 갱신하지 않도록 새로운 자료구조를 만들어야 한다
### 19.2.2 트리를 사용한 다른 예제
### 19.2.3 함수형 접근법 사용
- 일반적인 메서드는 모든 사용자가 같은 자료구조를 공유하며 프로그램에서 누군가 자료구조를 갱신했을 때 영향을 받는다
- 함수형 코드는 결과 자료구조를 바꾸지 않는다. 영속.
- ex. 비함수형 코드 : 누군가 언제든 트리를 갱신할 수 있으므로 트리에 어떤 구조체의 값을 추가할 때마다 값을 복사
- ex. 함수형 코드 : 새로운 Tree를 만든다. 하지만 인수를 이용해서 가능한 한 많은 정보를 공유한다. 영속.

## 19.3 스트림과 게으른 평가
### 19.3.1 자기 정의 스트림
#### 소수 스트림 예제
1단계 : 스트림 숫자 얻기  -> 2단계 : 머리 획득  -> 3단계 : 꼬리 필터링  -> 4단계 : 재귀적으로 소수 스트림 생성
- 최종연산을 스트림에 호출하면 스트림이 완전 소비된다는 점 주의
- 무한 재귀에 빠짐

#### 게으른 평가 / 비엄격한 평가 / 이름에 의한 호출
- 처리할 필요가 있을 때만 스트림을 실제로 평가한다.
- 실제 스트림을 소비해야 하는 상황이 되었을 때 인수를 평가한다.
- 스칼라의 #:: 연산자 : 게으른 연결 담당
### 19.3.2 게으른 리스트 만들기
- 자바 8의 스트림은 게으르다. 요청할 때만 값을 생성하는 블랙박스와 같다
- 스트림에 일련의 연산을 적용하면 연산이 수행되지 않고 일단 저장됨
- 스트림에 최종 연산을 적용해서 실제 계산을 해야 하는 상황에서만 실제 연산이 이루어진다
- 게으른 특성 때문에 각 연산별로 스트림을 탐색할 필요 없이 한 번에 여러 연산을 처리할 수 있다
## 19.4 패턴 매칭
#### 패턴 매칭
자료형을 언랩하는 함수형 기능
### 19.4.1 방문자 디자인 패턴
- 자바에서는 방문자 디자인 패턴으로 자료형을 언랩할 수 있다
- 특정 데이터 형식을 ‘방문’하는 알고리즘을 캡슐화하는 클래스를 따로 만들 수 있다

### 19.4.2 패턴 매칭의 힘
- 패턴 매칭을 지원하는 언어의 가장 큰 실용적인 장점은 아주 커다란 switch문이나 if-then-else 문을 피할 수 있다

## 19.5 기타 정보
### 19.5.1 캐싱 또는 기억화
#### 기억화
- 메서드에 래퍼로 캐시(HashMap 같은)를 추가하는 기법
- 래퍼가 호출되면 인수, 결과 쌍이 캐시에 존재하는지 먼저 확인한다. 
- 캐시에 값이 존재하면 캐시에 저장된 값을 즉시 반환
- 없으면 computeNumberOfNodes를 호출해서 결과를 계산한 다음에 새로운 인수, 결과 쌍을 캐시에 저장하고 결과를 반환
- 캐싱, 즉 다수의 호출자가 공유하는 자료구조를 갱신하는 기법이므로 순수 함수형 해결방식은 아니지만 감싼 버전의 코드는 참조 투명성 유지 가능
- 함수형 프로그래밍을 사용해서 동시성과 가변 상태가 만나는 상황을 완전히 없애는 것이 제일 좋은 방법!
### 19.5.2 '같은 객체를 반환함'은 무엇을 의미하는가?
#### 참조 투명성
- 인수가 같다면 결과도 같아야 한다
- 일반적으로 함수형 프로그래밍에서는 데이터가 변경되지 않으므로 같다는 의미는 ==(참조가 같음)이 아니라 구조적인 값이 같다는 것을 의미
### 19.5.3 콤비네이터
- 함수를 조합하는 기능
- ex. 두 함수를 인수로 받아 다른 함수를 반환
#### thenCombine()
- CompletableFuture클래스의 메서드
- CompletableFuture와 BiFunction 두 인수를 받아 새로운 CompletableFuture를 생성

#### compose()
- f와 g를 인수로 받아서 f의 기능을 적용한 다음에 g의 기능을 적용하는 함수를 반환
- 콤비네이터로 내부 반복을 수행하는 동작 정의 가능

## 19.6 마치며
- 일급 함수란 인수로 전달하거나, 결과로 반환하거나, 자료구조에 저장할 수 있는 함수다.
- 고차원 함수란 한 개 이상의 함수를 인수로 받아서 다른 함수를 반환하는 함수다. 자바에서는 comparing, andThen, compose 등의 고차원 함수를 제공한다
- 커링은 함수를 모듈화하고 코드를 재사용할 수 있도록 지원하는 기법이다. 
- 영속 자료구조는 갱신될 때 기존 버전의 자신을 보존한다. 결과적으로 자신을 복사하는 과정이 따로 필요하지 않다. 
- 자바의 스트림은 스스로 정의할 수 없다. 
- 게으른 리스트는 자바 스트림보다 비싼 버전으로 간주할 수 있다. 게으른 리스트는 데이터를 요청했을 때 Supplier를 이용해서 요소를 생성한다. Supplier는 자료구조의 요소를 생성하는 역할을 수행한다.
- 패턴 매칭은 자료형을 언랩하는 함수형 기능이다. 자바의 switch문을 일반화할 수 있다.
- 참조 투명성을 유지하는 상황에서는 계산 결과를 캐시할 수 있다.
- 콤비네이터는 둘 이상의 함수나 자료구조를 조합하는 함수형 개념이다