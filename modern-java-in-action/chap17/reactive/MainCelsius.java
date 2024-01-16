package chap17.reactive;

import java.util.concurrent.Flow.Publisher;

public class MainCelsius {
    public static void main(String[] args) {
        // 뉴욕의 섭씨 온도를 전송할 Publisher를 만들고
        // TempSubscriber를 Publisher로 구독
        getCelsiusTemperatures("New York").subscribe(new TempSubscriber());
    }

    public static Publisher<TempInfo> getCelsiusTemperatures(String town) {
        return subscriber -> {
            // TempProcessor를 만들고 Subscriber와 반환된 Publisher 사이로 연결
            TempProcessor processor = new TempProcessor();
            processor.subscribe(subscriber);
            processor.onSubscribe(new TempSubscription(processor, town));
        };
    }
}
