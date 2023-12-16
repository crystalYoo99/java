package December;

public class S5_4673{
    public static void main(String[] args) {
        int set[] = new int[10001];

        for (int i = 1; i < 9999; i++) {
            int sumnum = 0;
            int temp = i;
            while(temp > 0) {
                sumnum += (temp % 10);
                temp /= 10;
            }
            if(i + sumnum <= 10000) {
                set[i + sumnum] = 1;
            }
        }
        for (int i = 1; i < 10001; i ++) {
            if (set[i] == 0) System.out.println(i);
        }
    }
}