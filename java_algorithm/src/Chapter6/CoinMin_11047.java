package Chapter6;

import java.io.*;
import java.util.*;

public class CoinMin_11047 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        int[] A = new int[N];
        int cnt = 0;

        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }

        for (int i = N-1; i >= 0; i--) {
            if (K >= A[i]) {
                int tmp = K/A[i];
                K -= A[i]*tmp;
                cnt += tmp;
            }
        }

        System.out.println(cnt);
    }
}