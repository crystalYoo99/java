package chap09.designPattern.templateMethod;

// 온라인 뱅킹 애플리케이션의 동작을 정의하는 추상 클래스
abstract class OnlineBanking {
    // 온라인 뱅킹 알고리즘이 해야 할 일. 고객 ID로 고객 만족시켜야 함.
    // 각 지점은  OnlineBanking 클래스를 상속받아 makeCustomerHappy 메서드가 원하는 동작을 수행하도록 구현
    public void processCustomer(int id) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy(c);
    }

    abstract void makeCustomerHappy(Customer c);

    // 더미 Customer 클래스
    static private class Customer {}

    // 더미 Database 클래스
    static private class Database {
        static Customer getCustomerWithId(int id) {
            return new Customer();
        }
    }
}
