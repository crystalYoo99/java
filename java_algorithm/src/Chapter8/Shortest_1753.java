package Chapter8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Shortest_1753 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(br.readLine());
        ArrayList<ArrayList<sNode>> A = new ArrayList<>();
        int[] result = new int[V+1];
        boolean[] visited = new boolean[][V+1];

        for (int i = 0; i <= V; i++) {
            A.add(new ArrayList<>());
            result[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            A.get(u).add(new sNode(v, w));
        }

        Queue<sNode> que = new LinkedList<>();
        que.offer(new sNode(K, 0));
        result[K] = 0;

        while (!que.isEmpty()) {
            sNode curr = que.poll();
            int curr_v = curr.target;
            if (visited[curr_v]) continue;
            visited[curr_v] = true;
        }
    }
}

class sNode {
    int target;
    int value;

    sNode (int target, int value) {
        this.target = target;
        this.value = value;
    }
}
