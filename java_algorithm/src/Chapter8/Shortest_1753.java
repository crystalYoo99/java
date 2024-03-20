package Chapter8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Shortest_1753 {
    public static int V, E, K;
    public static int[] distance;
    public static boolean[] visited;
    public static ArrayList<sNode>[] list;
    public static PriorityQueue<sNode> q = new PriorityQueue<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(br.readLine());

        distance = new int[V+1];
        visited = new boolean[V+1];
        list = new ArrayList[V+1];

        for (int i = 1; i <= V; i++) {
            list[i] = new ArrayList<>();
        }
        for (int i = 0; i <= V; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            list[u].add(new sNode(v, w));
        }

        q.add(new sNode(K, 0));
        distance[K] = 0;

        while (!q.isEmpty()) {
            sNode curr = q.poll();
            int curr_v = curr.vertex;
            if (visited[curr_v]) continue;
            visited[curr_v] = true;

            for (int i = 0; i < list[curr_v].size(); i++) {
                sNode tmp = list[curr_v].get(i);
                int next = tmp.vertex;
                int val = tmp.value;
                if (distance[next] > distance[curr_v] + val) {
                    distance[next] = val + distance[curr_v];
                    q.add(new sNode(next, distance[next]));
                }
            }
        }
        for (int i = 1; i <= V; i++) {
            if (visited[i]) System.out.println(distance[i]);
            else System.out.println("INF");
        }
    }
}

class sNode implements Comparable<sNode> {
    int vertex;
    int value;

    sNode (int vertex, int value) {
        this.vertex = vertex;
        this.value = value;
    }
    public int compareTo(sNode s) {
        if (this.value > s.value) return 1;
        else return -1;
    }
}
