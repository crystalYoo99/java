package chap10.dsl;

import chap10.dsl.model.Order;
import chap10.dsl.model.Trade;
import chap10.dsl.model.Stock;

public class MethodChainingOrderBuilder {
    public final Order order = new Order(); // 빌더로 감싼 주문

    private MethodChainingOrderBuilder(String customer) {
        order.setCustomer(customer);
    }

    public static MethodChainingOrderBuilder forCustomer(String customer) {
        return new MethodChainingOrderBuilder(customer);
    } // 고객의 주문을 만드는 정적 팩토리 메서드

    // buy, sell 메서드는 다른 주문 만들어 추가할 수 있게 자신 만들어 반환
    public TradeBuilder buy(int quantity) {
        return new TradeBuilder(this, Trade.Type.BUY, quantity);
    } // 주식을 사는 TradeBuilder

    public TradeBuilder sell(int quantity) {
        return new TradeBuilder(this, Trade.Type.SELL, quantity);
    } // 주식을 파는 TradeBuilder

    public MethodChainingOrderBuilder addTrade(Trade trade) {
        order.addTrade(trade); //주문에 주식 추가
        return this; // 유연하게 추가 주문 만들어 추가할 수 있게 주문 빌더 자체 반환
    }

    public Order end() {
        return order; // 주문 만들기 종료하고 반환
    }

    // 빌더를 계속 이어가려면 Stock 클래스의 인스턴스 만드는 TradeBuilder의 공개 메서드 이용해야 함.
    public class TradeBuilder {
        private final MethodChainingOrderBuilder builder;
        public final Trade trade = new Trade();

        private TradeBuilder(MethodChainingOrderBuilder builder, Trade.Type type, int quantity) {
            this.builder = builder;
            trade.setType(type);
            trade.setQuantity(quantity);
        }

        public StockBuilder stock(String symbol) {
            return new StockBuilder(builder, trade, symbol);
        }
    }

    // StockBuilder는 주식의 시장을 지정하고, 거래에 주식을 추가하고, 최종 빌더를 반환하는 on( ) 메서드 한 개를 정의
    public class StockBuilder {
        private final MethodChainingOrderBuilder builder;
        private final Trade trade;
        private final Stock stock = new Stock();

        private StockBuilder(MethodChainingOrderBuilder builder, Trade trade, String symbol) {
            this.builder = builder;
            this.trade = trade;
            stock.setSymbol(symbol);
        }

        // 주식 시장 지정, 거래에 주식 추가, 최종 빌더 반환
        public TradeBuilderWithStock on(String market) {
            stock.setMarket(market);
            trade.setStock(stock);
            return new TradeBuilderWithStock(builder, trade);
        }
    }

    // TradeBuilderWithStock은 거래되는 주식의 단위 가격을 설정한 다음 원래 주문 빌더를 반환
    //  MethodChainingOrderBuilder가 끝날 때까지 다른 거래를 플루언트 방식으로 추가 가능
    public class TradeBuilderWithStock {
        private final MethodChainingOrderBuilder builder;
        private final Trade trade;

        public TradeBuilderWithStock(MethodChainingOrderBuilder builder, Trade trade)
        {
            this.builder = builder;
            this.trade = trade;
        }
        public MethodChainingOrderBuilder at(double price) {
            trade.setPrice(price);
            return builder.addTrade(trade);
        }
    }
}
