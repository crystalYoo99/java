package chap09.designPattern;

import java.util.ArrayList;
import java.util.List;

public class ObserverMain {
    // 다양한 신문 매체(뉴욕타임스, 가디언, 르몽드)가 뉴스 트윗을 구독하고 있으며 특정 키워드를 포함하는 트윗이 등록되면 알림
    public static void main(String[] args) {
        Feed f = new Feed();
        f.registerObserver(new NYTimes());
        f.registerObserver(new Guardian());
        f.registerObserver(new LeMonde());
        f.notifyObservers("The queen said her favourite book is Java 8 & 9 in Action!");

        // 람다 표현식 사용
        // 옵저버를 명시적으로 인스턴스화하지 않고 람다 표현식을 직접 전달해서 실행할 동작을 지정
        Feed feedLambda = new Feed();
        feedLambda.registerObserver((String tweet) -> {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY! " + tweet);
            }
        });
        feedLambda.registerObserver((String tweet) -> {
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("Yet another news in London... " + tweet);
            }
        });
        feedLambda.notifyObservers("Money money money, give me money!");
    }

    // 1. 다양한 옵저버를 그룹화할 Observer 인터페이스
    // 새로운 트윗이 있을 때 주제(Feed)가 호출할 수 있도록 notify라고 하는 하나의 메서드
    interface Observer {
        void notify(String tweet);
    }

    // 3. 주제. Subject 인터페이스
    interface Subject {
        void registerObserver(Observer o);
        void notifyObservers(String tweet);
    }

    // 2. 트윗에 포함된 다양한 키워드에 다른 동작을 수행할 수 있는 여러 옵저버 정의
    static private class NYTimes implements Observer {
        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY!" + tweet);
            }
        }
    }

    static private class Guardian implements Observer {
        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("Yet another news in London... " + tweet);
            }
        }
    }

    static private class LeMonde implements Observer {
        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("wine")) {
                System.out.println("Today cheese, wine and news! " + tweet);
            }
        }
    }

    // 4. 주제의 구현
    // registerObserver 메서드로 새로운 옵저버를 등록한 다음 notifyObservers 메서드로 트윗의 옵저버에 이를 알린다
    // Feed는 트윗을 받았을 때 알림을 보낼 옵저버 리스트를 유지
    static private class Feed implements Subject {
        private final List<Observer> observers = new ArrayList<>();

        @Override
        public void registerObserver(Observer o) {
            observers.add(o);
        }

        @Override
        public void notifyObservers(String tweet) {
            observers.forEach(o -> o.notify(tweet));
        }
    }
}
