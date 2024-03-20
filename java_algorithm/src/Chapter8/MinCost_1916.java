package Chapter8;

import java.io.*;
import java.util.*;

public class MinCost_1916 {
    static int N, M;
    static ArrayList<Node>[] list;
    static int[] dist;
    static boolean[] visit;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        list = new ArrayList[N+1];
        dist = new int[N+1];
        visit = new boolean[N+1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        for (int i = 0; i <= N; i++) {
            list[i] = new ArrayList<Node>();
        }

        StringTokenizer st;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            list[start].add(new Node(end, weight));
        }
        st = new StringTokenizer(br.readLine());
        int startIdx = Integer.parseInt(st.nextToken());
        int endIdx = Integer.parseInt(st.nextToken());

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(dijkstra(startIdx, endIdx) + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    public static int dijkstra(int start, int end) {
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        pq.offer(new Node(start, 0));
        dist[start] = 0;

        while (!pq.isEmpty()) {
            Node currNode = pq.poll();
            int curr = currNode.target;
            if (!visit[curr]) {
                visit[curr] = true;
                for (Node n : list[curr]) {
                    if (!visit[n.target] && dist[n.target] > dist[curr] + n.value) {
                        dist[n.target] = dist[curr] + n.value;
                        pq.add(new Node(n.target, dist[n.target]));
                    }
                }
            }
        }
        return dist[end];
    }
}
class Node implements Comparable<Node> {
    int target;
    int value;
    Node (int target, int value) {
        this.target = target;
        this.value = value;
    }
    @Override
    public int compareTo(Node o) {
        return value - o.value;
    }
}