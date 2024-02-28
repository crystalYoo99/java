package Chapter5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Dfsbfs_1260 {
    static boolean[] visited;
    static ArrayList<Integer>[] A;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int V = sc.nextInt();

        A = new ArrayList[N+1];
        for (int i = 1; i <= N; i++) {
            A[i] = new ArrayList<>();
        }

        int n1, n2;
        for (int i = 0; i < M; i++) {
            n1 = sc.nextInt();
            n2 = sc.nextInt();
            A[n1].add(n2);
            A[n2].add(n1);
        }

        for (int i = 1; i <= N; i++) {
            Collections.sort(A[i]);
        }



        // 방문 배열 초기화
        visited = new boolean[N+1];
        // DFS
        DFS(V);
        System.out.println();

        // BFS
        visited = new boolean[N+1];
        BFS(V);
        System.out.println();

    }
    static void BFS(int target) {
        Queue<Integer> que = new LinkedList<Integer>();
        que.add(target);
        visited[target] = true;

        while (!que.isEmpty()) {
            int curr = que.poll();
            System.out.print(curr + " ");
            for (int i : A[curr]) {
                if (!visited[i]) {
                    visited[i] = true;
                    que.add(i);
                }
            }
        }
    }

    static void DFS(int target) {
        visited[target] = true;
        System.out.print(target + " ");

        for (int i : A[target]) {
            if (!visited[i]) {
                DFS(i);
            }
        }
    }
}
