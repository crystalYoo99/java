package Chapter7;

import java.util.Scanner;

public class NonSquare_1016 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long min = sc.nextLong();
        long max = sc.nextLong();
        boolean[] A = new boolean[(int)(max-min+1)];

        for (long i = 2; i * i <= max; i++) {
            long tmp = i*i;
            long start = min / tmp;
            if (min % tmp != 0) start++;
            for (long j = start; tmp*j <= max; j++) {
                A[(int)((j*tmp) - min)] = true;
            }
        }
        int cnt = 0;
        for (int i = 0; i <= max-min; i++) {
            if (!A[i]) cnt++;
        }
        System.out.println(cnt);
    }
}