package Chapter8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class FindCity_18352 {
    static int[] visited;
    static ArrayList<Integer>[] arr;
    static int N, M, K, X;
    static List<Integer> answer;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        arr = new ArrayList[N+1];
        visited = new int[N+1];
        answer = new ArrayList<>();

        for (int i = 1; i <= N; i++) {
            arr[i] = new ArrayList<>();
        }
        for (int i = 0; i <= N; i++) {
            visited[i] = -1;
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            arr[A].add(B);
        }
        BFS(X);
        for (int i = 0; i <= N; i++) {
            if (visited[i] == K) {
                answer.add(i);
            }
        }
        if (answer.isEmpty()) {
            System.out.println("-1");
        } else {
            Collections.sort(answer);
            for (int a: answer) {
                System.out.println(a);
            }
        }
    }
    private static void BFS(int Node) {
        Queue<Integer> que = new LinkedList<>();
        que.add(Node);
        visited[Node]++;
        while(!que.isEmpty()) {
            int curr = que.poll();
            for (int i: arr[curr]) {
                if (visited[i] == -1) {
                    visited[i] = visited[curr] + 1;
                    que.add(i);
                }
            }
        }
    }
}