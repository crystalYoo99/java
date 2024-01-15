package chap16.asynchronous;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BestPriceFinder {
    private final List<Shop> shops = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy"));

    // 모든 상점에 순차적으로 정보를 요청하는 findPrices
    public List<String> findPricesSequentialOrigin(String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f",
                        shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }
    public List<String> findPricesSequential(String product) {
        return shops.stream()
                // 각 상점에서 할인 전 가격 얻기
                .map(shop -> shop.getPrice(product))
                // 상점에서 반환한 문자열을 Quote 객체로 변환
                .map(Quote::parse)
                // Discount 서비스를 이용해서 각 Quote에 할인 적용
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    //  findPrices 메서드 병렬화
    public List<String> findPricesParallelOrigin(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f",
                        shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }
    public List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    // CompletableFuture로 findPrices 구현하기
    public List<String> findPricesFuture(String product) {
        // 스트림은 게으른 특성이 있어서 두 개의 스트림 파이프라인으로 처리
        // 하나로 했으면 모든 가격 정보 요청 동작이 동기적, 순차적으로 이뤄지기 때문
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        // CompletableFuture로 각각의 가격을 비동기적으로 계산
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getName() + " price is " +
                                        shop.getPrice(product)))
                        .collect(Collectors.toList());

        return priceFutures.stream()
                // 모든 비동기 동작이 끝나길 기다림
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }



    // 최저가격 검색 애플리케이션에 맞는 커스텀 Executor
    private final Executor executor =
            // 상점 수만큼의 스레드를 갖는 풀을 생성
            // 스레드 수의 범위는 0~100
            Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                    new ThreadFactory() {
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread(r);
                            // 프로그램 종료를 방해하지 않는 데몬 스레드를 사용
                            t.setDaemon(true);
                            return t;
                        }
                    });

    public List<String> findPricesFutureCustomExecutorOrigin(String product) {
        // 스트림은 게으른 특성이 있어서 두 개의 스트림 파이프라인으로 처리
        // 하나로 했으면 모든 가격 정보 요청 동작이 동기적, 순차적으로 이뤄지기 때문
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        // CompletableFuture로 각각의 가격을 비동기적으로 계산
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getName() + " price is " +
                                        shop.getPrice(product), executor))
                        .collect(Collectors.toList());

        return priceFutures.stream()
                // 모든 비동기 동작이 끝나길 기다림
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public List<String> findPricesFutureCustomExecutor(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getPrice(product), executor))
                        .map(future -> future.thenApply(Quote::parse))
                        .map(future -> future.thenCompose(quote ->
                                CompletableFuture.supplyAsync(
                                        () -> Discount.applyDiscount(quote), executor)))
                        .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
