package Chapter3;

import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.IOException;

public class RestSum_10986 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		long A[] = new long[N+1];
		long C[] = new long[N];
		long answer = 0;
		
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
			A[i] += A[i-1];
			A[i] %= (int)M;
			if (A[i] == 0) {
				answer += 1;
			}
			C[(int)A[i]]++;
		}
		
		for(int i = 0; i < M; i++) {
			if (C[i] > 1) {
				answer = answer + (C[i] * (C[i] -1) /2);
			}
		}
		
		System.out.println(answer);
	}
}
