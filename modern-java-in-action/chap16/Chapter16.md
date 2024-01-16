# Chapter 16. CompletableFuture : 안정적 비동기 프로그래밍
> Keyword : 비동기 작업, 비블록 동작, 비동기 API

## 16.1  Future의 단순 활용
### 16.1.1 Future 제한
#### Future
- 비동기 계산을 모델링하는 데 이용
- 시간이 걸릴 수 있는 작업을 Future 내부로 설정하면 호출자 스레드가 결과를 기다리는 동안 다른 유용한 작업 수행 가능
- 시간이 오래 걸리는 작업을 Callable 객체 내부로 감싼 다음에 ExecutorService에 제출해서 사용
- Future의 get 메서드로 결과 가져올 때 아직 준비되지 않았으면 완료될때까지 블록시킴
- 따라서 get 메서드를 오버로드해서 대기할 최대 타임아웃 시간을 설정하는 게 좋음

#### Future 제한
- isDone() : Future 인터페이스가 비동기 계산이 끝났는지 확인할 수 있는 메서드
- isDone 메서드, 계산 끝나길 기다리는 메서드, 결과 회수 메서드 등이 있지만 간결한 동시 실행 코드 구현 어려움
- 선언형 기능이 필요. 그래서 CompletableFuture 클래스 이용.

### 16.1.2 CompletableFuture로 비동기 애플리케이션 만들기
#### CompletableFuture
- Future 인터페이스를 구현한 클래스
- CompletableFuture는 Stream처럼 람다 표현식과 파이프라이닝을 활용

#### 동기 API와 비동기 API
- 동기 API에서는 메서드를 호출한 다음에 메서드가 계산을 완료할 때까지 기다렸다가 메서드가 반환되면 호출자는 반환된 값으로 계속 다른 동작을 수행
- 비동기 API에서는 메서드가 즉시 반환되며 끝내지 못한 나머지 작업을 호출자 스레드와 동기적으로 실행될 수 있도록 다른 스레드에 할당
- 동기 API를 사용하는 상황은 블록 호출 / 비동기 API를 사용하는 상황은 비블록 호출

## 16.2 비동기 API 구현
### 16.2.1 동기 메서드를 비동기 메서드로 변환
### 16.2.2 에러 처리 방법
- 타임아웃값을 받는 get 메서드의 오버로드 버전으로 영원히 블록되는 문제 해결
- 타임아웃 시간이 지나면 TimeoutException
- completeExceptionally() 로 CompletableFuture 내부에서 발생한 예외 전달
#### 팩토리 메서드 supplyAsync로 CompletableFuture 만들기
- supplyAsync() : Supplier를 인수로 받아서 CompletableFuture를 반환
- CompletableFuture는 Supplier를 실행해서 비동기적으로 결과를 생성 
- ForkJoinPool의 Executor 중 하나가 Supplier를 실행
- 이 때, 두 번째 인수를 받는 오버로드 버전의 supplyAsync 메서드로 다른 Executor 지정 가능

## 16.3 비블록 코드 만들기
### 16.3.1 병렬 스트림으로 요청 병렬화하기
### 16.3.2 CompletableFuture로 비동기 호출 구현하기
스트림은 게으른 특성이 있다는 것을 고려해서 두 개 이상의 스트림 파이프라인으로 처리
### 16.3.3 더 확장성이 좋은 해결 방법
- CompletableFuture는 병렬 스트림 버전에 비해 작업에 이용할 수 있는 다양한 Executor를 지정할 수 있다
- Executor로 스레드 풀의 크기를 조절하는 등 애플리케이션에 맞는 최적화된 설정
#### 스트림 병렬화와 CompletableFuture 병렬화
- I/O가 포함되지 않은 계산 중심의 동작을 실행할 때는 스트림 인터페이스
- I/O를 기다리는 작업을 병렬로 실행할 때는 CompletableFuture
### 16.3.4 커스텀 Executor 사용하기
- 애플리케이션의 특성에 맞는 Executor를 만들어 CompletableFuture를 활용하는 것이 바람직
- 애플리케이션이 실제로 필요한 작업량을 고려한 풀에서 관리하는 스레드 수에 맞게 Executor를 만들기
- 스레드 수가 너무 많으면 서버가 크래시될 수 있으므로 하나의 Executor에서 사용할 스레드의 최대 개수는 100 이하로 설정
#### 스레드 풀
- 너무 크면 CPU와 메모리 자원을 경쟁하느라 시간 낭비 / 너무 작으면 CPU 일부 코어는 활용되지 않음
- N_threads = N_CPU * U_CPU * (1 + W/C) 로 대략적인 CPU 활용 비율 계산
- N_CPU : Runtime.getRuntime().availableProcessors()가 반환하는 코어 수
- U_CPU : 0과 1 사이의 값을 갖는 CPU 활용 비율
- W/C : 대기시간과 계산시간의 비율
#### 데몬 스레드
- 자바에서 일반 스레드가 실행 중이면 자바 프로그램은 종료되지 않음
- 데몬 스레드는 자바 프로그램이 종료될 때 강제로 실행 종료 가능

## 16.4 비동기 작업 파이프라인 만들기
### 16.4.1 할인 서비스 구현
### 16.4.2 할인 서비스 사용
### 16.4.3 동기 작업과 비동기 작업 조합하기
#### thenApply 메서드
CompletableFuture가 끝날 때까지 블록하지 않는다
#### thenCompose 메서드
- CompletableFuture API에서 두 비동기 연산을 파이프라인으로 만들 수 있도록 제공하는 메서드. 
- Async로 끝나지 않는 메서드는 이전 작업을 수행한 스레드와 같은 스레드에서 작업 실행. 
- Async로 끝나는 메서드는 다음 작업이 다른 스레드에서 실행되도록 스레드 풀로 작업 제출.
### 16.4.4 독립 CompletableFuture와 비독립 CompletableFuture 합치기
- 독립적으로 실행된 두 개의 CompletableFuture 결과를 합쳐야 하는 상황 발생
- 첫 번째 CompletableFuture의 동작 완료와 관계없이 두 번째 CompletableFuture를 실행할 수 있어야 한다
#### thenCombine 메서드
- BiFunction을 두 번째 인수로 받음
- BiFunction은 두 개의 CompletableFuture 결과를 어떻게 합칠지 정의
- thenCombineAsync() : BiFunction이 정의하는 조합 동작이 스레드 풀로 제출되면서 별도의 태스크에서 비동기적으로 수행
- ex. 두 CompletableFuture의 결과가 생성되고 BiFunction으로 합쳐진 다음에 세 번째 CompletableFuture를 얻을 수 있다
### 16.4.5 Future의 리플렉션과 CompletableFuture의 리플렉션
- CompletableFuture는 람다 표현식을 사용
- 람다 덕분에 다양한 동기 태스크, 비동기 태스크를 활용해서 복잡한 연산 수행 방법을 효과적으로 쉽게 정의할 수 있는 선언형 API를 만들 수 있다
### 16.4.6 타임아웃 효과적으로 사용하기
#### orTimeout 메서드
- 지정된 시간이 지난 후에 CompletableFuture를 TimeoutException으로 완료하면서 또 다른 CompletableFuture를 반환할 수 있도록 내부적으로 ScheduledThreadExecutor를 활용
- 계산 파이프라인을 연결하고 여기서 TimeoutException이 발생했을 때 사용자가 쉽게 이해할 수 있는 메시지 제공 가능
#### completeOnTimeout 메서드
- 서버에서 얻은 값이 아닌 미리 지정된 값을 사용
- CompletableFuture에 타임아웃이 발생하면 기본값으로 처리
- orTimeout()처럼  CompletableFuture를 반환해서 이 결과를 다른 CompletableFuture 메서드와 연결 가능

## 16.5 CompletableFuture의 종료에 대응하는 방법
### 16.5.1 최저가격 검색 애플리케이션 리팩터링
일련의 연산 실행 정보를 포함하는 CompletableFuture의 스트림을 직접 제어
Future 스트림을 반환하게 메서드 리팩터링
#### thenAccept 메서드
- 연산 결과를 소비하는 Consumer를 인수로 받는다
- CompletableFuture가 생성한 결과를 어떻게 소비할지 미리 지정했으므로 CompletableFuture<Void>를 반환
- thenAcceptAsync() : CompletableFuture가 완료된 스레드가 아니라 새로운 스레드를 이용해서 Consumer를 실행
#### allOf 메서드
- CompletableFuture 배열을 입력으로 받아 CompletableFuture<Void>를 반환
- 전달된 모든 CompletableFuture가 완료되어야 CompletableFuture<Void>가 완료됨
- allOf()가 반환하는 CompletableFuture에 join을 호출하면 원래 스트림의 모든 CompletableFuture의 실행 완료를 기다릴 수 있다
#### anyOf 메서드
- 배열의 CompletableFuture 중 하나의 작업이 끝나길 기다리는 상황
- CompletableFuture 배열을 입력으로 받아서 CompletableFuture<Object>를 반환
- CompletableFuture<Object>는 처음으로 완료한 CompletableFuture의 값으로 동작을 완료
### 16.5.2 응용

## 16.6 로드맵
플로 API : CompletableFuture(연산 또는 값으로 종료하는 일회성 기법)의 기능이 한 번에 종료되지 않고 일련의 값을 생산하도록 일반화

## 16.7 마치며
- 한 개 이상의 원격 외부 서비스를 시용하는 긴 동작을 실행할 때는 비동기 방식으로 애플리케이션의 성능과 반응성을 향상시킬 수 있다.
- 우리 고객에게 비동기 API를 제공하는 것을 고려해야 한다. CompletableFuture의 기능을 이용하면 쉽게 비동기 API를 구현할 수 있다.
- CompletableFuture를 이용할 때 비동기 태스크에서 발생한 에러를 관리하고 전달할 수 있다.
- 동기 API를 CompletableFuture로 감싸서 비동기적으로 소비할 수 있다. 
- 서로 독립적인 비동기 동작이든 아니면 하나의 비동기 동작이 다른 비동기 동작의 결과에 의존하는 상황이든 여러 비동기 동작을 조립하고 조합할 수 있다.
- CompletableFuture에 콜백을 등록해서 Future가 동작을 끝내고 결과를 생산했을 때 어떤 코드를 실행하도록 지정할 수 있다.
- CompletableFuture 리스트의 모든 값이 완료될 때까지 기다릴지 아니면 첫 값만 완료되길 기다릴지 선택할 수 있다.
- 자바 9에서는 orTimeout, completeOnTimeout 메서드로 CompletableFuture에 비동기 타임아웃 기능을 추가했다.
