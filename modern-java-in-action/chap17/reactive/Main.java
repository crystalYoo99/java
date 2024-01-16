package chap17.reactive;

import java.util.concurrent.Flow.Publisher;

public class Main {
    public static void main(String[] args) {
        // 뉴욕에 새 Publisher를 만들고 TempSubscriber를 구독시킴
        getTemperatures("New York").subscribe(new TempSubscriber());
    }

    // 구독한 Subscriber에게 TempSubscription을 전송하는 Publisher를 반환
    private static Publisher<TempInfo> getTemperatures(String town) {
        return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
    }
}
