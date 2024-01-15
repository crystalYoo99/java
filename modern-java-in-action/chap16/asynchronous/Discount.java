package chap16.asynchronous;

import static chap16.asynchronous.Util.delay;
import static chap16.asynchronous.Util.format;

public class Discount {
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
        private final int percentage;
        Code(int percentage) {
            this.percentage = percentage;
        }
    }
    // 생략된 Discount 클래스 구현은 [예제 16-14] 참조

    public static String applyDiscount(Quote quote) {
        // 기존 가격에 할인 코드를 적용
        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    private static double apply(double price, Code code) {
        // Discount 서비스의 응답 지연을 흉내
        delay();
        return format(price * (100 - code.percentage) / 100);
    }
}