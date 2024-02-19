package Chapter3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class GoodNumber_1253 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.valueOf(br.readLine());
		long A[] = new long[N];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for (int i = 0; i < N; i++) {
			A[i] = Long.valueOf(st.nextToken());
		}
		
		int cnt = 0;
		Arrays.sort(A);
		for (int i = 0; i < N; i++) {
			int start_idx = 0;
			int end_idx = N-1;
			while(start_idx < end_idx) {
				if(A[start_idx] + A[end_idx] == A[i]) {
					if (start_idx != i && end_idx != i) {
						cnt ++;
						break;
					}
					else if (start_idx == i) {
						start_idx ++;
					}
					else if (end_idx == i) {
						end_idx --;
					}
 				}
				else if(A[start_idx] + A[end_idx] > A[i]) {
					end_idx --;
				}
				else {
					start_idx ++;
				}
			}
		}
		System.out.println(cnt);
	}
}
