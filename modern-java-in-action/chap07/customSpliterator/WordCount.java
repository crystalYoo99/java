package chap07.customSpliterator;

import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class WordCount {
    // 문자열의 단어 수를 계산
    public static final String SENTENCE =
            " Nel   mezzo del cammin  di nostra  vita "
                    + "mi  ritrovai in una  selva oscura"
                    + " che la  dritta via era   smarrita ";

    public static void main(String[] args) {
        // 반복형으로 단어 수는 세는 메서드
        System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");

        // 함수형으로 단어 수를 세는 메서드 재구현하기
        // 반복형 대신 함수형을 이용하면 직접 스레드를 동기화하지 않고도 병렬 스트림으로 작업을 병렬화 가능
        System.out.println("Found " + countWords(SENTENCE) + " words");

        // 아래처럼 병렬 스트림 처리 불가. 순차 스트림을 병렬 스트림으로 바꿀 때 스트림 분할 위치에 따라 잘못된 결과 나올수도.
        // 문자열을 임의의 위치에서 분할하지 않고 단어가 끝나는 위치에서만 분할하게!
        //System.out.println("Found " + countWords(stream.parallel()) + " words");

        // 새로운 WordCounterSpliterator를 병렬 스트림에 사용
        System.out.println("Found " + countWords(SENTENCE) + " words");
    }

    public static int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) { //문자열의 모든 문자를 하나씩 탐색한다.
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) counter++;
                lastSpace = false;
            }
        }
        return counter;
    }

    // 새로운 WordCounterSpliterator를 병렬 스트림에 사용
    public static int countWords(String s) {
        //Stream<Character> stream = IntStream.range(0, s.length()).mapToObj(SENTENCE::charAt).parallel();
        Spliterator<Character> spliterator = new WordCounterSpliterator(s);
        Stream<Character> stream = StreamSupport.stream(spliterator, true);

        return countWords(stream);
    }

    private static int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        return wordCounter.getCounter();
    }

}
