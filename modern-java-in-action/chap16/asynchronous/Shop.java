package chap16.asynchronous;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static chap16.asynchronous.Util.delay;

public class Shop {
    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public double getPriceOrigin(String product) {
        double price = calculatePrice(product);
        return price;
    }

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[
                random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread( () -> {
            try {
                double price = calculatePrice(product);
                // 계산이 정상적으로 종료되면 Future에 가격 정보를 저장한 채로 Future를 종료
                futurePrice.complete(price);
            } catch (Exception ex) {
                // 도중에 문제가 발생하면 발생한 에러를 포함시켜 Future를 종료
                futurePrice.completeExceptionally(ex);
            }
        }).start();
        return futurePrice;
    }

    public Future<Double> getPriceAsyncSimple(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }


    public double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public String getName() {
        return name;
    }
}
