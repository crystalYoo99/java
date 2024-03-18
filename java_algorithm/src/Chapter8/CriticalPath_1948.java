package Chapter8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class CriticalPath_1948 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int M = Integer.parseInt(br.readLine());

        ArrayList<ArrayList<dNode>> A = new ArrayList<>();
        ArrayList<ArrayList<dNode>> reverseA = new ArrayList<>();

        for (int i = 0; i <= N; i++) {
            A.add(new ArrayList<>());
            reverseA.add(new ArrayList<>());
        }

        int[] degree = new int[N+1];
        for (int i =0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int S = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());
            int V = Integer.parseInt(st.nextToken());

            A.get(S).add(new dNode(E, V));
            reverseA.get(E).add(new dNode(S, V));
            degree[E]++;
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        Queue<Integer> que = new LinkedList<>();
        que.offer(start);

        int[] result = new int[N+1];
        while (!que.isEmpty()) {
            int curr = que.poll();
            for (dNode next : A.get(curr)) {
                degree[next.target]--;
                result[next.target] = Math.max(result[next.target], result[curr] + next.value);
                if (degree[next.target] == 0) que.offer(next.target);
            }
        }

        int resultCnt = 0;
        boolean[] visited = new boolean[N+1];
        que = new LinkedList<>();
        que.offer(end);
        visited[end] = true;
        while(!que.isEmpty()) {
            int curr = que.poll();
            for (dNode next : reverseA.get(curr)) {
                if (result[next.target] + next.value == result[curr]) {
                    resultCnt++;
                    if (visited[next.target] == false) {
                        visited[next.target] = true;
                        que.offer(next.target);
                    }
                }
            }
        }
        System.out.println(result[end]);
        System.out.println(resultCnt);
    }
}

class dNode {
    int target;
    int value;
    dNode (int target, int value) {
        this.target = target;
        this.value = value;
    }
}