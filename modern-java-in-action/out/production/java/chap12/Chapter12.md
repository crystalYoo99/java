# Chapter 12. 새로운 날짜와 시간 API
> Keyword : 날짜와 시간 라이브러리, 날짜 조작, 포매팅, 파싱, 시간대와 캘린더

## 12.1 LocalDate, LocalTime, Instant, Duration, Period 클래스 (java.time 패키지)
### 12.1.1 LocalDate와 LocalTime 사용
#### LocalDate, LocalTime
- LocalDate 인스턴스는 시간을 제외한 날짜를 표현하는 불변 객체
- LocalDate 객체는 어떤 시간대 정보도 포함하지 않음
- 정적 팩토리 메서드 of로 LocalDate, LocalTime 인스턴스를 만들 수 있다
- 정적 메서드 parse와 문자열, DateTimeFormatter로도 만들 수 있다
- LocalDate : get, getYear(), getMonthValue(), getDayOfMonth()
- LocalTime : getHour(), getMinute(), getSecond()

#### TemporalField
- 시간 관련 객체에서 어떤 필드의 값에 접근할지 정의하는 인터페이스
- 열거자 ChronoField :  TemporalField 인터페이스를 정의

### 12.1.2 날짜와 시간 조합 (LocalDateTime)
- LocalDate와 LocalTime을 쌍으로 갖는 복합 클래스
- LocalDateTime을 만드는 방법과 날짜와 시간을 조합하는 방법
- LocalDate의 atTime 메서드에 시간을 제공, LocalTime의 atDate 메서드에 날짜를 제공하는 방법
- LocalDateTime의 toLocalDate나 toLocalTime 메서드로 LocalDate나 LocalTime 인스턴스를 추출 가능

### 12.1.3 Instant 클래스 : 기계의 날짜와 시간
- 기계의 관점에서는 연속된 시간에서 특정 지점을 하나의 큰 수로 표현
- 유닉스 에포크 시간(1970년 1월 1일 0시 0분 0초 UTC)을 기준으로 시간을 초로 표현
- 팩토리 메서드 ofEpochSecond에 초를 넘겨줘서 Instant 클래스 인스턴스 생성
- Instant는 사람이 읽을 수 있는 시간 정보를 제공하지 않음

### 12.1.4 Duration과 Period 정의
#### Duration과 Period
- Duration 클래스의 정적 팩토리 메서드 between : 두 시간 객체 사이의 지속시간
- Duration: 두 개의 LocalTime, 두 개의 LocalDateTime, 또는 두 개의 Instant로 Duration
- Period 클래스의 팩토리 메서드 between : 두 LocalDate 사이
- of, ofMinutes, ofDays, ofWeeks : 두 시간 객체를 사용하지 않고도 만들 수 있음

## 12.2 날짜 조정, 파싱, 포매팅
### 날짜조정 (TemporalAdjusters) 
#### 절대적 : withAttribute / 상대적 : plus, minus 메서드
Temporal 인터페이스의 다양한 메서드로 날짜를 조정할 수 있다
#### TemporalAdjusters
- 오버로드된 버전의 with 메서드에 좀 더 다양한 동작을 수행할 수 있도록 하는 기능을 제공
- nextOrSame, lastDayOfMonth 등 다양한 TemporalAdjusters 클래스의 팩토리 메서드
- adjustInto 메서드만 정의하는 함수형  인터페이스
- Temporal 객체를 어떻게 다른 Temporal 객체로 변환할지 정의
- 커스텀 TemporalAdjuster 구현도 가능
### 날짜와 시간 객체 출력과 파싱 (format)
#### DateTimeFormatter
- 날짜나 시간을 특정 형식의 문자열로
- 날짜나 시간을 표현하는 문자열을 파싱해서 다시 날짜 객체로
- 정적 팩토리 메서드와 상수를 이용해서 손쉽게 포매터 만들 수 있음
- BASIC_ISO_DATE와 ISO_LOCAL_DATE 등의 상수 미리 있음
#### DateTimeFormatterBuilder
복합적인 포매터를 정의해서 좀 더 세부적으로 포매터 제어 가능

## 12.3 다양한 시간대와 캘린더 활용 방법
### 12.3.1 시간대 사용하기
### 12.3.2 UTC/Greenwich 기준의 고정 오프셋
### 12.3.3 대안 캘린더 시스템 사용하기

## 12.4 마치며
- 자바 8 이전 버전에서 제공하는 기존의 java.util.Date 클래스와 관련 클래스에서는 여러 불일치점들과 가변성, 어설픈 오프셋, 기본값, 잘못된 이름 결정 등의 설계 결함이 존재했다.
- 새로운 날짜와 시간 API에서 날짜와 시간 객체는 모두 불변이다.
- 새로운 API는 각각 사람과 기계가 편리하게 날짜와 시간 정보를 관리할 수 있도록 두 가지 표현 방식을 제공한다.
- 날짜와 시간 객체를 절대적인 방법과 상대적인 방법으로 처리할 수 있으며 기존 인스턴스를 변환하지 않도록 처리 결과로 새로운 인스턴스가 생성된다.
- TemporalAdjuster를 이용하면 단순히 값을 바꾸는 것 이상의 복잡한 동작을 수행할 수 있으며 자신만의 커스텀 날짜 변환 기능을 정의할 수 있다.
- 날짜와 시간 객체를 특정 포맷으로 출력하고 파싱하는 포매터를 정의할 수 있다. 패턴을 이용하거나 프로그램으로 포매터를 만들 수 있으며 포매터는 스레드 안정성을 보장한다.
- 특정 지역/장소에 상대적인 시간대 또는 UTC/GMT 기준의 오프셋을 이용해서 시간대를 정의할 수 있으며 이 시간대를 날짜와 시간 객체에 적용해서 지역화할 수 있다.
- ISO-8601 표준 시스템을 준수하지 않는 캘린더 시스템도 사용할 수 있다.
