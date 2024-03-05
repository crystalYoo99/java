# 2장 컴퓨터 네트워크
## 2.2 TCP와 UDP
> Keyword : TCP, TCP 핸드셰이킹, 패킷 교환 방식 (가상회선/데이터그램), 플래그(SYN, FIN, ACK), TCP 제어 방법(흐름, 혼잡, 오류), UDP

### TCP란
- 전송 계층에 해당하는 네트워크 프로토콜
- 연결형 서비스를 지원하고 데이터의 신뢰성을 보장

### TCP의 특징
- 송신부와 수신부의 연결을 확인하는 연결형 서비스
- 패킷 교환 방식은 패킷이 전달되는 회선이 정해져있는 가상 회선 방식
- 패킷의 전송 순서 보장
- 패킷의 수신 여부 확인
- 송신부와 수신부는 1:1 통신 함
- 데이터 손실 없음을 보장해서 신뢰성 높음
- 데이터의 송수신 속도 느림

#### 패킷 교환 방식
- 가장 많이 사용하는 데이터 통신 방식. 가상 회선방식과 데이터그램 방식 있음.
- 가상회선방식 : 데이터를 주고 받기 전에 패킷을 전송할 경로인 가상 회선을 설정해서 모든 패킷을 같은 경로로 전송
- 데이터그램 방식 : 패킷마다 최적의 경로로 전송되는 방식. 송신부에서 보낸 패킷의 순서와 수신부에 도착하는 패킷의 순서 다를 수 있음.

### TCP 핸드셰이킹
- TCP에서는 연결형 서비스를 지원하기 위해 송신부와 수신부를 연결하는 과정 거침
- 연결시작: 3-way 핸드셰이킹 / 연결종료: 4-way 핸드셰이킹
- 핸드셰이킹 과정에서는 송신부와 수신부 간 연결을 제어 및 관리하도록 플래그 값 주고 받음.

#### 플래그 (flag)
- SYN : Synchronization(동기화). 연결 생성 시 사용.
- FIN : Finish(종료). 연결 끊을 때 사용
- ACK : Acknowledgment(승인). 데이터를 전송하면 수신자가 받았음을 알려줄 때 사용
- RST : Reset(초기화). 연결 재설정 시 사용
- PSH : Push(밀다). 빠른 응답이 필요한 데이터를 응용 계층으로 즉시 전송할 때 사용
- URG : Urgent(긴급). 다른 데이터보다 우선순위가 높은 데이터를 전송할 때 사용

#### 3-way 핸드셰이킹
- 데이터를 본격적으로 주고받기 전 상대방 컴퓨터와 세션을 수립하는 과정
- 요청과 응답을 총 3번 주고 받음
- 송신부와 수신부 간에 번갈아 요청과 응답을 해서 연결 확인 후에 본격적인 데이터 통신 하게 됨.
  ![TCP 3-way 핸드셰이킹](./src/2_10.png)

1. 송신부가 수신부와 연결하기 위해 SYN 메시지 보냄. 이 때 임의의 숫자 N을 같이 보냄. 
- 송신부는 수신부로부터 응답오기 전까지 SYN_SENT 상태.
2. 수신부가 송신부로부터 SYN 메시지 받으면 연결 요청 수락하는 의미인 ACK 메시지 전송.
- ACK 메시지에는 송신부로부터 받은 N에 1을 더한 N+1 값 같이 보냄.
- 수신부에서도 송신부와의 연결을 확인하기 위해 SYN 메시지에 임의의 숫자인 M 보낸 후, 송신부 응답 기다림.
- 수신부는 SYN_RECEIVED 상태
3. 송신부가 수신부로부터 ACK + SYN 메시지를 받으면 연결이 성립되었따는 의미인 established 상태가 됨.
- 메시지 대한 응답으로 ACK 메시지와 M+1 값 같이 보냄.
- 이 때 ACK 메시지에는 송신부에서 전송하려는 데이터 포함될 수 있음.
- 송신부로부터 ACK 메시지 받으면 수신부는 established 상태 됨.

#### 4-way 핸드셰이킹
- TCP 연결을 해제할 때 이뤄지는 과정
- 요청과 응답을 총 4번 주고받음
  ![TCP 4-way 핸드셰이킹](./src/2_11.png)

1. 송신부가 수신부와 연결 종료하려고 FIN 메시지 보냄. 송신부는 FIN_WAIT1 상태.
2. 수신부가 FIN 받으면 응답으로 ACK 메시지 보냄. 수신부는 CLOSE_WAIT 상태.
- 수신부는 메시지 보낸 후 앱 종료하는 등 연결 종료하기 위한 작업 함.
- 송신부에서는 수신부에서 보낸 ACK 메시지 받고 FIN_WAIT2 상태 됨.
3. 수신부에서 연결 종료 준비 끝나면 송신부에 FIN 메시지 보내고 LAST_WAIT 상태됨.
4. 송신부는 서버로부터 받은 FIN 메시지에 응답하기 위해 ACK 메시지 보내고 TIME_WAIT 상태.
- 일정 시간 지나면 CLOSED 상태됨. 
- 일정 시간 TIME_WAIT 상태 유지하는 이유는 FIN 메시지 전에 보낸 패킷이 FIN 메시지 수신보다 지연되어 발생하는 패킷 유실에 대비하기 위함
- 수신부에 ACK 메시지가 제대로 전달되지 않아 연결 해제 이뤄지는 경우도 많음
- 수신부는 송신부로부터 ACK 메시지 받고 CLOSED 상태됨.

### TCP 제어 방법
- TCP의 데이터 신뢰성을 보장하기 위한 제어 방법
- 흐름 제어 / 혼잡 제어 / 오류 제어 

#### 흐름 제어 (flow control)
- 데이터 송신부와 수신부에서 데이터 처리 속도의 차이 때문에 생기는 데이터 손실을 방지하는 방법
1. 정지-대기(stop-wait) : 송신부에서 데이터 보낸 후 수신부로부터 ACK 메시지 받을 때까지 다음 데이터 보내지 않고 기다리는 방식
2. 슬라이딩 윈도우(sliding window) : 송신부에서 ACK 확인하지 않고 수신부에서 설정한 윈도우 크기만큼 데이터를 연속적으로 보낼 수 있게 해서 데이터 흐름을 동적으로 제어
   
![정지-대기 작동 방식](./src/2_12.png)
![슬라이딩 윈도우 작동 방식](./src/2_13.png)
![슬라이딩 윈도우 예](./src/2_14.png)



## 2.3 HTTP
## 2.4 REST