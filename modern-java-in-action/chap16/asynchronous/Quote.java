package chap16.asynchronous;

// 상점에서 얻은 문자열을 정적 팩토리 메서드 parse로 넘겨주면 상점 이름, 할인전 가격, 할인된 가격 정보를 포함하는 Quote
public class Quote {
    private final String shopName;
    private final double price;
    private final Discount.Code discountCode;
    public Quote(String shopName, double price, Discount.Code code) {
        this.shopName = shopName;
        this.price = price;
        this.discountCode = code;
    }
    public static Quote parse(String s) {
        String[] split = s.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code discountCode = Discount.Code.valueOf(split[2]);
        return new Quote(shopName, price, discountCode);
    }
    public String getShopName() {
        return shopName;
    }
    public double getPrice() {
        return price;
    }
    public Discount.Code getDiscountCode() {
        return discountCode;
    }
}
