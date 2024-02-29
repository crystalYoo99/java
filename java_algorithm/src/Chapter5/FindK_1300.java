package Chapter5;

import java.util.*;
import java.io.*;

public class FindK_1300 {
    static int N;
    static int k;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        k = sc.nextInt();

        binarySearch(1, k);
    }
    static void binarySearch(long start, long end) {
        long target = 0;
        while (true) {
            long mid = (start + end)/2;
            if (start > end) break;
            long sum = 0;
            for (int i = 1; i <= N; i++) {
                sum += Math.min(mid/i, N);
            }
            if (sum >= k) {
                end = mid - 1;
                target = mid;
            }
            else {
                start = mid + 1;
            }
        }
        System.out.println(target);
    }
}