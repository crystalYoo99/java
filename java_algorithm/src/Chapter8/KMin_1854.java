package Chapter8;

import java.io.*;
import java.nio.Buffer;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class KMin_1854 {
    static final int INF = 100000000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[][] W = new int[1001][1001];
        PriorityQueue<Integer>[] que = new PriorityQueue[N+1];
        Comparator<Integer> cp = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 < o2 ? 1 : -1;
            }
        };
        for (int i = 0; i < N+1; i++) {
            que[i] = new PriorityQueue<>(K, cp);
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            W[a][b] = c;
        }
        PriorityQueue<kNode> pq = new PriorityQueue<>();
        pq.add(new kNode(1, 0));
        que[1].add(0);
        while(!pq.isEmpty()) {
            kNode u = pq.poll();
            for (int adjNode = 1; adjNode <= N; adjNode++) {
                if (W[u.node][adjNode] != 0) {
                    if (que[adjNode].size() < K) {
                        que[adjNode].add(u.cost + W[u.node][adjNode]);
                        pq.add(new kNode(adjNode, u.cost + W[u.node][adjNode]));
                    }
                    else if (que[adjNode].peek() > u.cost + W[u.node][adjNode]) {
                        que[adjNode].poll();
                        que[adjNode].add(u.cost + W[u.node][adjNode]);
                        pq.add(new kNode(adjNode, u.cost + W[u.node][adjNode]));
                    }
                }
            }
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i = 1; i <= N; i++) {
            if (que[i].size() == K) {
                bw.write(que[i].peek() + "\n");
            } else {
                bw.write(-1 + "\n");
            }
        }
        bw.flush();
        bw.close();
        br.close();
    }
}

class kNode implements Comparable<kNode> {
    int node;
    int cost;
    kNode(int node, int cost) {
        this.cost = cost;
        this.node = node;
    }
    @Override
    public int compareTo(kNode o) {
        return this.cost < o.cost ? -1 : 1;
    }
}
