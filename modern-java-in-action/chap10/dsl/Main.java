package chap10.dsl;

import chap10.dsl.model.Order;
import chap10.dsl.model.Trade;
import chap10.dsl.model.Stock;

import static chap10.dsl.MethodChainingOrderBuilder.forCustomer;
import static chap10.dsl.NestedFunctionOrderBuilder.*;
import static chap10.dsl.LambdaOrderBuilder.*;
import static chap10.dsl.MixedBuilder.*;
import static chap10.dsl.MixedBuilder.forCustomer;




public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.plain();
        main.methodChain();
        main.nestedFunction();
        main.lambda();
        main.mixed();
    }

    public void plain() {
        Order order = new Order();
        order.setCustomer("BigBank");

        Trade trade1 = new Trade();
        trade1.setType(Trade.Type.BUY);

        Stock stock1 = new Stock();
        stock1.setSymbol("IBM");
        stock1.setMarket("NYSE");

        trade1.setStock(stock1);
        trade1.setPrice(125.00);
        trade1.setQuantity(80);
        order.addTrade(trade1);

        Trade trade2 = new Trade();
        trade2.setType(Trade.Type.BUY);

        Stock stock2 = new Stock();
        stock2.setSymbol("GOOGLE");
        stock2.setMarket("NASDAQ");

        trade2.setStock(stock2);
        trade2.setPrice(375.00);
        trade2.setQuantity(50);
        order.addTrade(trade2);

        System.out.println("Plain:");
        System.out.println(order);
    }

    public void methodChain() {
        Order order = forCustomer("BigBank")
                .buy(80).stock("IBM").on("NYSE").at(125.00)
                .sell(50).stock("GOOGLE").on("NASDAQ").at(375.00)
                .end();
        System.out.println("Method chaining:");
        System.out.println(order);
    }

    public void nestedFunction() {
        Order order = order("BigBank",
                buy(80, stock("IBM", on("NYSE")), at(125.00)),
                sell(50, stock("GOOGLE", on("NASDAQ")), at(375.00))
        );
        System.out.println("Nested Function:");
        System.out.println(order);
    }

    public void lambda() {
        Order order = order( o -> {
            o.forCustomer("BigBank");
            o.buy(t -> {
                t.quantity(80);
                t.price(125.00);
                t.stock(s -> {
                    s.symbol("IBM");
                    s.market("NYSE");
                });
            });
            o.sell(t -> {
                t.quantity(50);
                t.price(375.00);
                t.stock(s -> {
                    s.symbol("GOOGLE");
                    s.market("NASDAQ");
                });
            });
        });
        System.out.println("Lambda - Function Sequencing:");
        System.out.println(order);
    }

    public void mixed() {
        //  중첩된 함수 패턴을 람다 기법과 혼용
        // TradeBuilder의 Consumer가 만든 각 거래는 람다 표현식으로 구현
        Order order = forCustomer("BigBank", // 최상위 수준 주문의 속성을 지정하는 중첩 함수
                        buy( t -> t.quantity(80) // 한 개의 주문을 만드는 람다 표현식
                                .stock("IBM") // 거래 객체를 만드는 람다 표현식 바디의 메서드 체인
                                .on("NYSE")
                                .at(125.00)),
                        sell( t -> t.quantity(50)
                                .stock("GOOGLE")
                                .on("NASDAQ")
                                .at(125.00)));
    }
}
