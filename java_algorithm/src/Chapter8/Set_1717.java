package Chapter8;

import java.io.*;
import java.util.*;
public class Set_1717 {
    static int n, m;
    static int[] A;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        A = new int[n+1];

        for (int i = 1; i <= n; i++) {
            A[i] = i;
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int target = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (target == 0) {
                union(Math.min(a, b), Math.max(a, b));
            }
            else {
                if (find(a) == find(b)) System.out.println("YES");
                else System.out.println("NO");
            }
        }
    }
    static void union(int x, int y) {
        x = find(x);
        y = find(y);
        if (x != y)
            A[y] = x;
    }
    static int find(int x) {
        if (A[x] == x) return x;
        else return A[x] = find(A[x]);
    }
}
