package chap10.dsl;

import chap10.dsl.model.Order;
import chap10.dsl.model.Stock;
import chap10.dsl.model.Trade;

import java.util.stream.Stream;
import java.util.function.Consumer;


public class MixedBuilder {
    public static Order forCustomer(String customer, TradeBuilder... builders) {
        Order order = new Order();
        order.setCustomer(customer);
        Stream.of(builders).forEach(b -> order.addTrade(b.trade));
        return order;
    }

    public static TradeBuilder buy(Consumer<TradeBuilder> consumer) {
        return buildTrade(consumer, Trade.Type.BUY);
    }

    public static TradeBuilder sell(Consumer<TradeBuilder> consumer) {
        return buildTrade(consumer, Trade.Type.SELL);
    }

    private static TradeBuilder buildTrade(Consumer<TradeBuilder> consumer, Trade.Type buy) {
        TradeBuilder builder = new TradeBuilder();
        builder.trade.setType(buy);
        consumer.accept(builder);
        return builder;
    }

    // 헬퍼 클래스 TradeBuilder와 StockBuilder는 내부적으로 메서드 체인 패턴을 구현해 플루언트 API를 제공
    public static class TradeBuilder {
        private Trade trade = new Trade();

        public TradeBuilder quantity(int quantity) {
            trade.setQuantity(quantity);
            return this;
        }

        public TradeBuilder at(double price) {
            trade.setPrice(price);
            return this;
        }

        public StockBuilder stock(String symbol) {
            return new StockBuilder(this, trade, symbol);
        }
    }

    public static class StockBuilder {
        private final TradeBuilder builder;
        private final Trade trade;
        private final Stock stock = new Stock();

        private StockBuilder(TradeBuilder builder, Trade trade, String symbol) {
            this.builder = builder;
            this.trade = trade;
            stock.setSymbol(symbol);
        }

        public TradeBuilder on(String market) {
            stock.setMarket(market);
            trade.setStock(stock);
            return builder;
        }
    }
}
