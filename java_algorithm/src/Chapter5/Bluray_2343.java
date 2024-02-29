package Chapter5;

import java.util.*;
import java.io.*;

public class Bluray_2343 {
    static int[] A;
    static int N;
    static int M;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        A = new int[N];
        int Max = 0;
        int Sum = 0;

        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
            if (A[i] > Max) Max = A[i];
            Sum += A[i];
        }

        System.out.println(binary(Max, Sum));
    }
    static int binary(int start, int end) {
        while (start <= end) {
            int mid = (start + end)/2;
            int sum = 0;
            int cnt = 0;

            for (int i = 0; i < N; i++) {
                if (sum + A[i] > mid) {
                    cnt++;
                    sum = 0;
                }
                sum += A[i];
            }
            if (sum != 0) cnt++;
            if (cnt > M) start = mid + 1;
            else end = mid - 1;
        }
        return start;
    }
}