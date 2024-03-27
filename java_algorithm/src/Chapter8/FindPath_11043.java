package Chapter8;

import java.io.*;
import java.util.*;


public class FindPath_11043 {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static int N;
    static int dist[][];

    public static void main(String[] args) {
        N = Integer.parseInt(br.readLine());
        dist = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int v = Integer.parseInt(st.nextToken());
                dist[i][j] = v;
            }
        }
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (dist[i][k] == 1 && dist[k][j] == 1) dist[i][j] = 1;
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(distance[i][j] + " ");
            }
            System.out.println();
        }
    }
}