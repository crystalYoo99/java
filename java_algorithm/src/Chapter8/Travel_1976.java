package Chapter8;

import java.util.*;
import java.io.*;
public class Travel_1976 {
    static int[] parent;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        parent = new int[N];

        for (int i = 0; i < N; i++) {
            parent[i] = i;
        }
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int temp = Integer.parseInt(st.nextToken());
                if (temp == 1 && parent[i] != parent[j]) union(i, j);
            }
        }

        st = new StringTokenizer(br.readLine());
        int curr = Integer.parseInt(st.nextToken())-1;
        for (int i = 0; i < M-1; i++) {
            int next = Integer.parseInt(st.nextToken())-1;
            if (find(curr) != find(next)) {
                System.out.println("NO");
                return;
            }
            else {
                curr = next;
            }
        }
        System.out.println("YES");
    }
    static void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a != b) parent[b] = a;
    }

    static int find(int x) {
        if (parent[x] == x) return x;
        else return parent[x] = find(parent[x]);
    }
}
