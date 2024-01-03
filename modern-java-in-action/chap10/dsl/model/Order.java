package chap10.dsl.model;

import java.util.ArrayList;
import java.util.List;

// 고객이 요청한 거래의 주문
public class Order {
    private String customer;
    private List<Trade> trades = new ArrayList<>();
    public void addTrade(Trade trade) {
        trades.add(trade);
    }
    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public double getValue() {
        return trades.stream().mapToDouble(Trade::getValue).sum();
    }
}
