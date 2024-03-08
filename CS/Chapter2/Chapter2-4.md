# 2장 컴퓨터 네트워크
## 2.4 REST
> Keyword : HTTP(비연결성 - HTTP Keep Alive, 무상태 - 쿠키, 세션), HTTPS

### REST (Representational State Transfer)
- HTTP 통신을 활용하기 위해 고안된 아키텍처
- Representational은 인터넷상의 자원을, URI(Uniform Resource Identifier)로 나타낼 수 있음을 의미
- 클라이언트는 URI로 표현된 자원을 HTTP 메서드를 이용해 CRUD 연산을 할 수 있따
- State Transfer는 자원의 상태를 주고 받는 것, 즉 요청받은 자원의 상태를 전달하는 것을 의미
- REST는 자원을 명시해 연산을 수행하고 상태를 주고받는 것이다.
- REST는 HTTP를 기반으로 한 플랫폼에서 범용으로 사용됨

#### REST의 특징
- 일관된 인터페이스
- 클라이언트-서버 구조
- 무상태성
- 캐싱 가능
- 자체 표현 구조
- 계층형 구조

### REST API
- REST를 기반으로 한 API
- API(Application Programming Interface) : 다른 소프트웨어에 서비스를 제공하기 위한 소프트웨어 인터페이스
- REST API는 REST를 기반으로 한 인터페이스
- 여러 기업에서 자체 서비스 제공을 위해 활용

### HTTP 메서드
- 클라이언트가 요청을 보낼 때 요청에 포함된 HTTP 메서드는 요청의 종류와 목적을 나타냄
- 주로 사용하는 메서드 : POST, GET, PUT, DELETE
  ![HTTP 메서드와 CRUD 연산](./src/2_31.png)

#### 그 외
- PATCH : 데이터 일부 갱신
- HEAD : 
- TRACE : 
- CONNECT : 
- OPTION : 