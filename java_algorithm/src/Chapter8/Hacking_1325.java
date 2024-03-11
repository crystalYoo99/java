package Chapter8;

import java.util.*;
import java.io.*;

public class Hacking_1325 {
    static int N, M;
    static boolean[] visited;
    static ArrayList<Integer>[] arr;
    static int[] answer;
    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();

        answer = new int[N+1];
        arr = new ArrayList[N+1];

        for (int i = 1; i < N+1; i++) {
            arr[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int A = sc.nextInt();
            int B = sc.nextInt();
            arr[A].add(B);
        }
        for (int i = 1; i < N+1; i++ ){
            visited = new boolean[N+1];
            BFS(i);
        }
        int maxVal = 0;
        for (int i = 1; i <= N; i++) {
            maxVal = Math.max(maxVal, answer[i]);
        }
        for (int i = 1; i <= N; i++) {
            if (answer[i] == maxVal) System.out.print(i+" ");
        }
    }

    static void BFS(int idx) {
        Queue<Integer> que = new LinkedList<>();
        que.add(idx);
        visited[idx] = true;
        while (!que.isEmpty()) {
            int curr = que.poll();
            for (int i : arr[curr]) {
                if (visited[i] == false) {
                    visited[i] = true;
                    answer[i] ++;
                    que.add(i);
                }
            }
        }
    }
}