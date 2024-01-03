package chap10.dsl;

import chap10.dsl.model.Order;
import chap10.dsl.model.Tax;

import java.util.function.DoubleUnaryOperator;

import static chap10.dsl.MixedBuilder.forCustomer;
import static chap10.dsl.MixedBuilder.*;



public class TaxCalculator {
    // 1단계 : 기본
    // 세금을 적용할 것인지 결정하는 불리언 플래그를 인수로 받는 정적 메서드
    public static double calculate(Order order, boolean useRegional, boolean useGeneral, boolean useSurcharge) {
        double value = order.getValue();
        if (useRegional) value = Tax.regional(value);
        if (useGeneral) value = Tax.general(value);
        if (useSurcharge) value = Tax.surcharge(value);
        return value;
    }

    // 2단계 : Method chaining - 최소 DSL
    private boolean useRegional;
    private boolean useGeneral;
    private boolean useSurcharge;
    public TaxCalculator withTaxRegional() {
        useRegional = true;
        return this;
    }
    public TaxCalculator withTaxGeneral() {
        useGeneral= true;
        return this;
    }
    public TaxCalculator withTaxSurcharge() {
        useSurcharge = true;
        return this;
    }
    public double calculate(Order order) {
        return calculate(order, useRegional, useGeneral, useSurcharge);
    }

    // 3단계 : Method references
    public DoubleUnaryOperator taxFunction = d -> d; // 주문값에 적용된 모든 세금을 계산하는 함수
    public TaxCalculator with(DoubleUnaryOperator f) {
        // 새로운 세금 계산 함수를 얻어서 인수로 전달된 함수와 현재 함수 합침
        taxFunction = taxFunction.andThen(f);
        // 유창하게 세금 함수가 연결될 수 있게 결과 반환
        return this;
    }
    public double calculateF(Order order) {
        // 주문 총 합에 세금 계싼 함수 적용해서 최종 주문값 계산
        return taxFunction.applyAsDouble(order.getValue());
    }

    public static void main(String[] args) {
        Order order =
                forCustomer("BigBank",
                        buy(t -> t.quantity(80)
                                .stock("IBM")
                                .on("NYSE")
                                .at(125.00)),
                        sell(t -> t.quantity(50)
                                .stock("GOOGLE")
                                .on("NASDAQ")
                                .at(125.00)));

        // 가독성의 문제. 불리언 변수의 올바른 순서를 기억하기 어렵고, 어떤 세금이 적용되었는지도 파악하기 어렵다
        double value = TaxCalculator.calculate(order, true, false, true);
        System.out.printf("Boolean arguments: %.2f%n", value);

        // 유창하게 불리언 플래그를 설정하는 최소 DSL을 제공하는 TaxCalculator
        // 지역 세금과 추가 요금은 주문에 추가하고 싶다는 점을 명확하게 보여줌.
        value = new TaxCalculator().withTaxRegional()
                .withTaxSurcharge()
                .calculate(order);
        System.out.printf("Method chaining: %.2f%n", value);

        // 메서드 참조
        value = new TaxCalculator().with(Tax::regional)
                .with(Tax::surcharge)
                .calculateF(order);
        System.out.printf("Method references: %.2f%n", value);
    }
}

