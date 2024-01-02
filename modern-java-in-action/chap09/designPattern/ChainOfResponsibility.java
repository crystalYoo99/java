package chap09.designPattern;

import java.util.function.Function;
import java.util.function.UnaryOperator;
public class ChainOfResponsibility {
    public static void main(String[] args) {
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();
        p1.setSuccessor(p2); // 3. 두 작업 처리 객체 연결
        String result1 = p1.handle("Aren't labdas really sexy?!!");
        System.out.println(result1);

        // 람다 표현식 사용
        // 함수 체인 (함수 조합) 과 비슷.
        // 작업 처리 객체를 UnaryOperator<String> 형식의 인스턴스로 표현.
        // andThen 메서드로 이들 함수를 조합해서 체인.
        UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;
        UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");
        Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
        String result2 = pipeline.apply("Aren't labdas really sexy?!!");
        System.out.println(result2);
    }

    // 1. 다음으로 처리할 객체 정보를 유지하는 필드를 포함하는 작업 처리 추상 클래스
    private static abstract class ProcessingObject<T> {
        protected ProcessingObject<T> successor;

        public void setSuccessor(ProcessingObject<T> successor) {
            this.successor = successor;
        }

        public T handle(T input) {
            T r = handleWork(input);
            if (successor != null) {
                return successor.handle(r);
            }
            return r;
        }

        abstract protected T handleWork(T input);
    }

    // 2. 작업 처리 객체들
    private static class HeaderTextProcessing extends ProcessingObject<String> {
        @Override
        public String handleWork(String text) {
            return "From Raoul, Mario and Alan: " + text;
        }
    }

    private static class SpellCheckerProcessing extends ProcessingObject<String> {
        @Override
        public String handleWork(String text) {
            return text.replaceAll("labda", "lambda");
        }
    }

}
