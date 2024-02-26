package Chapter5;

import java.io.*;
import java.util.*;

public class Friend_13023 {
    static ArrayList<Integer>[] A;
    static boolean[] visited;
    static int result;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.valueOf(st.nextToken());
        int M = Integer.valueOf(st.nextToken());
        A = new ArrayList[N];
        visited = new boolean[N];

        for (int i = 0; i < N; i++) {
            A[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < M; i++) {
            st =  new StringTokenizer(br.readLine());
            int a = Integer.valueOf(st.nextToken());
            int b = Integer.valueOf(st.nextToken());
            A[a].add(b);
            A[b].add(a);
        }

        for (int i = 0; i < N; i++) {
            DFS(i, 1);
            if(result == 1) break;
        }
        System.out.println(result);
    }

    static void DFS(int v, int cnt) {
        if(cnt >= 5 || result == 1) {
            result = 1;
            return;
        }

        visited[v] = true;
        for (int i : A[v]) {
            if (visited[i] == false) {
                DFS(i, cnt+1);
            }
        }
        visited[v] = false;
    }
}