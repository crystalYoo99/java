package December;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.IOException;

public class S2_1535 {
	static int lost[];
	static int get[];
	static int dp[][];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.valueOf(br.readLine());
		lost = new int[N+1];
		get = new int[N+1];
		dp = new int[N+1][101];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++) {
			lost[i] = Integer.valueOf(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++) {
			get[i] = Integer.valueOf(st.nextToken());
		}
		
		int answer = knapsack(N, 99);
		System.out.println(answer);
	}
	
	public static int knapsack(int N, int k) {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= k; j++) {
				if (lost[i] > j) {
					dp[i][j] = dp[i-1][j];
				}
				else {
					dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-lost[i]] + get[i]);
				}
			}
		}
		return dp[N][k];
	}
}