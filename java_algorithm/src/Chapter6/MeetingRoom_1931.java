package Chapter6;

import java.util.*;
import java.io.*;

public class MeetingRoom_1931 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[][] A = new int[N][2];
        int cnt = 0;
        int curr = -1;

        for (int i = 0; i < N; i++) {
            A[i][0] = sc.nextInt();
            A[i][1] = sc.nextInt();
        }

        Arrays.sort(A, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] == o2[1]) return o1[0] - o2[0];
                return o1[1] - o2[1];
            }
        });

        for (int i = 0; i < N; i++) {
            if (A[i][0] >= curr) {
                curr = A[i][1];
                cnt++;
            }
        }
        System.out.println(cnt);
    }
}
