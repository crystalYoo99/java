package Chapter8;

import java.io.*;
import java.util.*;

public class Game_1516 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        ArrayList<ArrayList<Integer>> A = new ArrayList<>();

        for (int i = 0; i <= N; i++) {
            A.add(new ArrayList<>());
        }

        int[] degree = new int[N+1];
        int[] selfBuild = new int[N+1];

        for (int i = 1; i < N+1; i++) {
            selfBuild[i] = sc.nextInt();
            while(true) {
                int tmp = sc.nextInt();
                if (tmp == -1) break;
                A.get(tmp).add(i);
                degree[i]++;
            }
        }
        Queue<Integer> que = new LinkedList<>();
        for (int i = 1; i < N+1; i++) {
            if (degree[i] == 0) {
                que.offer(i);
            }
        }
        int[] result = new int[N+1];
        while(!que.isEmpty()) {
            int now = que.poll();
            for (int next : A.get(now)) {
                degree[next]--;
                result[next] = Math.max(result[next], result[now]+selfBuild[now]);
                if (degree[next] == 0) {
                    que.offer(next);
                }
            }
        }
        for (int i = 1; i <= N; i++) {
            System.out.println(result[i] + selfBuild[i]);
        }
    }
}