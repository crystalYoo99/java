package chap09.designPattern;

public class StrategyMain {
    public static void main(String[] args) {
        // 예전 방식~
        Validator v1 = new Validator(new IsNumeric());
        System.out.println(v1.validate("aaaa"));
        Validator v2 = new Validator(new IsAllLowerCase());
        System.out.println(v2.validate("bbbb"));

        // 람다 표현식 사용
        Validator v3 = new Validator((String s) -> s.matches("\\d+"));
        System.out.println(v3.validate("aaaa"));
        Validator v4 = new Validator((String s) -> s.matches("[a-z]+"));
        System.out.println(v4.validate("bbbb"));
    }

    // 1. String 문자열을 검증하는 인터페이스 구현
    interface ValidationStrategy {
        boolean execute(String s);
    }

    // 2. 1에서 구현한 인터페이스를 구현하는 클래스를 하나 이상 정의
    static private class IsAllLowerCase implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("[a-z]+");
        }
    }

    static private class IsNumeric implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("\\d+");
        }
    }

    // 3. 구현한 클래스를 다양한 검증 전략으로 활용
    static private class Validator {

        private final ValidationStrategy strategy;

        public Validator(ValidationStrategy v) {
            this.strategy = v;
        }

        public boolean validate(String s) {
            return strategy.execute(s);
        }
    }
}
