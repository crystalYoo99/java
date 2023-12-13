package chap03.lambdaExercise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAround {

  private static final String FILE = ExecuteAround.class.getResource("./data.txt").getFile();

  public static void main(String... args) throws IOException {
    // processFileLimited을 더 유연하게 리팩토링
    String result = processFileLimited();
    System.out.println(result);
    System.out.println("---");

    // 1단계 : 동작 파라미터화를 기억하라
    // 2단계 : 함수형 인터페이스를 이용해서 동작 전달
    // 3단계 : 동작 실행
    // 4단계 : 람다 전달
    String oneLine = processFile((BufferedReader b) -> b.readLine());
    System.out.println(oneLine);
    String twoLines = processFile((BufferedReader b) -> b.readLine() + b.readLine());
    System.out.println(twoLines);

  }

  // 0단계
  public static String processFileLimited() throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
      return br.readLine();
    }
  }

  // 1. 동작 파라미터화
  // 람다를 이용해서 동작 전달

  //2. 함수형 인터페이스를 이용해서 동작 전달
  // 함수형 인터페이스 자리에 람다 사용.
  // BufferedReader -> String과 IOException을 던질 수 있는 시그니처와 일치하는 함수형 인터페이스

  // 3. 동작 실행
  //람다 표현식으로 함수형 인터페이스의 추상 메서드 구현을 직접 전달할 수 있으며
  // 전달된 코드는 함수형 인터페이스의 인스턴스로 전달된 코드와 같은 방식으로 처리
  // processFile 바디 내에서 BufferedReaderProcessor 객체의 process 호출
  public static String processFile(BufferedReaderProcessor p) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
      return p.process(br);
    }
  }
  @FunctionalInterface
  public interface BufferedReaderProcessor {
    String process(BufferedReader b) throws IOException;
  }
}
