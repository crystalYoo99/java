package Chapter8;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class BipatiteGraph_1707 {
    static int K, V, E;
    static boolean[] visited;
    static ArrayList<Integer>[] A;
    static int[] check;
    static boolean IsEven;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        K = Integer.parseInt(st.nextToken());
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            A = new ArrayList[V+1];
            visited = new boolean[V+1];
            check = new int[V+1];
            IsEven = true;

            for (int j = 1; j <= V; j++) {
                A[j] = new ArrayList<>();
            }
            for (int j = 0; j < E; j++) {
                st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                A[start].add(end);
                A[end].add(start);
            }
            for (int j = 1; j <= V; j++) {
                if (IsEven) DFS(j);
                else break;
            }
            if (IsEven) System.out.println("YES");
            else System.out.println("NO");
        }
    }

    static void DFS(int target) {
        visited[target] = true;
        for (int i : A[target]) {
            if (!visited[i]) {
                check[i] = (check[target] + 1) % 2;
                DFS(i);
            }
            else if (check[target] == check[i]) {
                IsEven = false;
            }
        }
    }
}
