package Chapter5;

import java.util.*;
import java.io.*;

public class WantNum_1920 {
    static int[] A;
    static int result;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = sc.nextInt();
        }
        Arrays.sort(A);

        int M = sc.nextInt();
        for (int i = 0; i < M; i++) {
            int tmp = sc.nextInt();
            result = binarySearch(0, A.length-1, tmp);
            System.out.println(result);
        }
    }

    static int binarySearch(int start, int end, int target) {
        if (end < start) return 0;
        else if (start == end) {
            if (A[start] == target)
                return 1;
            else
                return 0;
        }
        else {
            int mid = (start + end) / 2;
            if (target == A[mid]) return 1;
            else if (target > A[mid]) return binarySearch(mid+1, end, target);
            else return binarySearch(start, mid-1, target);
        }
    }
}