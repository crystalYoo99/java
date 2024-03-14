package Chapter8;

import java.util.*;

public class Line_2252 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        ArrayList<ArrayList<Integer>> A = new ArrayList<>();

        for (int i = 0; i <= N; i++) {
            A.add(new ArrayList<>());
        }

        int[] degree = new int[N+1];
        for (int i =0; i < M; i++) {
            int S = sc.nextInt();
            int E = sc.nextInt();
            A.get(S).add(E);
            degree[E]++;
        }

        Queue<Integer> que = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (degree[i] == 0) {
                que.offer(i);
            }
        }

        while (!que.isEmpty()) {
            int now = que.poll();
            System.out.print(now + " ");
            for (int next : A.get(now)) {
                degree[next]--;
                if (degree[next] == 0) {
                    que.offer(next);
                }
            }
        }
    }
}