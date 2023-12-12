package chap02.anonymousClass;

public class MeaningOfThis {
    public final int value = 4;
    public void doIt() {
        int value = 6;
        Runnable r = new Runnable() {
            public final int value = 5;
            public void run(){
                int value = 10;
                System.out.println(this.value);
                // this가 Runnable을 참조하기 때문에 답은 5
            }
        };
        r.run();
    }
    public static void main(String...args) {
        MeaningOfThis m = new MeaningOfThis();
        m.doIt();
    }
}
