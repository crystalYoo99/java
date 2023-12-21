package chap07.customSpliterator;

// 변수 상태를 캡슐화하는 새로운 클래스. 문자열 스트림을 탐색하면서 단어 수를 세는 클래스.
class WordCounter {
    private final int counter;
    private final boolean lastSpace;

    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    // WordCounter의 상태를 어떻게 바꿀 것인지,
    // 또는 엄밀히 WordCounter는 (속성을 바꿀 수 없는) 불변 클래스이므로 새로운 WordCounter 클래스를 어떤 상태로 생성할지 정의
    // 스트림을 탐색하면서 새로운 문자를 찾을 때마다 accumulate 메서드를 호출
    public WordCounter accumulate(Character c) { // 반복 알고리즘처럼 accumulate메서드는 문자열의 문자를 하나씩 탐색
        if (Character.isWhitespace(c)) {
            return lastSpace ? this : new WordCounter(counter, true);
        }
        else {
            // 문자를 하나씩 탐색하다 공백문자를 만나면 지금까지 탐색한 문자를 단어로 간주하여(공백 문자는 제외) 단어 수를 증가
            return lastSpace ? new WordCounter(counter + 1, false) : this;
        }
    }

    // WordCounter의 내부 counter값을 서로 합친다. 문자열 서브 스트림을 처리한 WordCounter의 결과를 합친다
    public WordCounter combine(WordCounter wordCounter) {
        // 두 WordCounter의 counter값을 더한다
        // counter 값만 더할 것이므로 마지막 공백은 신경 쓰지 않는다
        return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
    }

    public int getCounter() {
        return counter;
    }
}
