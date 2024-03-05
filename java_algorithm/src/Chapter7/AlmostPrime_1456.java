package Chapter7;

import java.util.*;
import java.io.*;
public class AlmostPrime_1456 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long A = sc.nextLong();
        long B = sc.nextLong();

        long[] Arr = new long[10000001];
        for (int i = 1; i < Arr.length; i++) {
            Arr[i] = i;
        }
        for (int i = 2; i <= Math.sqrt(Arr.length); i++) {
            if (Arr[i] == 0) continue;
            for (int j = i+i; j < Arr.length; j+=i) {
                Arr[j] = 0;
            }
        }
        int Sum = 0;
        for (int i = 2; i < 10000001; i++) {
            if (Arr[i] != 0) {
                long temp = Arr[i];
                while ((double)Arr[i] <= (double)B/(double)temp) {
                    if ((double)Arr[i] >= (double)A/(double)temp) {
                        Sum ++;
                    }
                    temp *= Arr[i];
                }
            }
        }
        System.out.println(Sum);
    }
}
