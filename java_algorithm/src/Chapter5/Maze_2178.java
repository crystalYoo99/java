package Chapter5;

import java.util.*;
import java.io.*;

public class Maze_2178 {
    static boolean[][] visited;
    static int[][] matrix;
    static int[][] connection = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
    static int N, M;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();

        visited = new boolean[N][M];
        matrix = new int[N][M];

        for (int i = 0; i < N; i++) {
            char[] tmp = sc.next().toCharArray();
            for (int j = 0; j < M; j++) {
                matrix[i][j] = Integer.parseInt(String.valueOf(tmp[j]));
            }
        }

        System.out.println(BFS(0, 0));
    }

    static int BFS(int v, int w) {
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[] {v, w});
        visited[v][w] = true;

        while(!que.isEmpty()) {
            int[] curr = que.poll();
            for (int i = 0; i < 4; i++) {
                int x = curr[0] + connection[i][0];
                int y = curr[1] + connection[i][1];
                if(x >= 0 && y >= 0 && x < N && y < M) {
                    if (!visited[x][y] && matrix[x][y] != 0) {
                        visited[x][y] = true;
                        matrix[x][y] = matrix[curr[0]][curr[1]] + 1;
                        que.add(new int[] {x, y});
                    }
                }
            }
        }

        return matrix[N-1][M-1];
    }
}