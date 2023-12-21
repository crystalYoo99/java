package chap07.customSpliterator;

import java.util.Spliterator;
import java.util.function.Consumer;

class WordCounterSpliterator implements Spliterator<Character> {
    private final String string;
    private int currentChar = 0;
    public WordCounterSpliterator(String string) {
        this.string = string;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(currentChar++)); // 현재 문자를 소비
        return currentChar < string.length(); // 소비할 문자가 남아있으면 true 반환
    }

    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;
        // 파싱할 문자열을 순차 처리할 수 있을만큼 충분히 작아졌음을 알리는 null 반환
        if (currentSize < 10) {
            return null;
        }
        // 파싱할 문자열의 중간을 분할 위치로 설정
        for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
            // 다음 공백이 나올 때까지 분할 위치를 뒤로 이동시킴
            if (Character.isWhitespace(string.charAt(splitPos))) {
                // 처음부터 분할 위치까지 문자열을 파싱할 새로운 WordCounterSpliterator 생성
                Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
                // 이 WordCounterSpliterator의 시작 위치를 분할 위치로 설정
                currentChar = splitPos;
                // 공백 찾았고 문자열 분리했으므로 루프 종료
                return spliterator;
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        // 탐색해야할 요소의 개수는
        // Spliterator가 파싱할 문자열 전체 길이 - 현재 반복 중인 위치
        return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
        //  ORDERED (문자열의 문자 등장 순서가 유의미함), SIZED (estimatedSize 메서드의 반환값이 정확함),
        // SUBSIZED (trySplit으로 생성된 Spliterator도 정확한 크기를 가짐),
        // NONNULL (문자열에는 null 문자가 존재하지 않음),
        // IMMUTABLE (문자열 자체가 불변 클래스이므로 문자열을 파싱하면서 속성이 추가되지 않음)
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}