package Chapter5;

import java.util.*;

public class Tree_1167 {
    static class Edge {
        int e;
        int value;
        public Edge (int e, int value) {
            this.e = e;
            this.value = value;
        }
    }
    static boolean[] visited;
    static int[] distance;
    static ArrayList<Edge>[] A;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int V = sc.nextInt();
        A = new ArrayList[V+1];

        for (int i = 1; i <= V; i++) {
            A[i] = new ArrayList<>();
        }
        for (int i = 1; i <= V; i++) {
            int target = sc.nextInt();
            while(true) {
                int tmp1 = sc.nextInt();
                if (tmp1 == -1) break;
                int tmp2 = sc.nextInt();
                A[target].add(new Edge(tmp1, tmp2));
            }
        }

        distance = new int[V+1];
        visited = new boolean[V+1];
        BFS(1);

        int Max = 1;
        for (int i = 2; i <= V; i++) {
            if (distance[Max] < distance[i]) {
                Max = i;
            }
        }

        distance = new int[V+1];
        visited = new boolean[V+1];
        BFS(Max);

        Arrays.sort(distance);
        System.out.println(distance[V]);
    }

    static void BFS(int idx) {
        Queue<Integer> que = new LinkedList<Integer>();
        que.add(idx);
        visited[idx] = true;
        while (!que.isEmpty()) {
            int curr = que.poll();
            for (Edge i : A[curr]) {
                int e = i.e;
                int v = i.value;
                if (!visited[e]) {
                    visited[e] = true;
                    que.add(e);
                    distance[e] = distance[curr] + v;
                }
            }
        }
    }
}