package chap09.designPattern.templateMethod;

import java.util.function.Consumer;

public class OnlineBankingLambda {
    public static void main(String[] args) {
        new OnlineBankingLambda().processCustomer(1337, (Customer c) -> System.out.println("Hello!"));
    }

    // makeCustomerHappy 메서드 시그니처랑 일치하도록
    // Consumer<Customer> 형식을 갖는 두 번째 인수를 processCustomer에 추가
    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }

    // 더미 Customer 클래스
    static private class Customer {}

    // 더미 Database 클래스
    static private class Database {
        static Customer getCustomerWithId(int id) {
            return new Customer();
        }
    }
}
