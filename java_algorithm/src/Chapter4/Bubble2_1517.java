package Chapter4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Bubble2_1517 {
	public static int N;
	public static int A[], tmp[];
	public static long answer;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.valueOf(br.readLine());
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		A =  new int[N+1];
		tmp = new int[N+1];
		
		for(int i = 1; i <= N; i++) {
			A[i] = Integer.valueOf(st.nextToken());
		}
		answer = 0;
		merge(1, N);
		
		System.out.println(answer);
	}
	
	private static void merge(int s, int e) {
		if (e - s < 1) {
			return;
		}
		int m = s + (e - s)/2;
		
		merge(s, m);
		merge(m+1, e);
		
		for (int i = s; i <= e; i++) {
			tmp[i] = A[i];
		}
		int k = s;
		int idx1 = s;
		int idx2 = m+1;
		while (idx1 <= m && idx2 <= e) {
			if (tmp[idx1] > tmp[idx2]) {
				A[k] = tmp[idx2];
				answer += (idx2 - k);
				k++;
				idx2++;
			} else {
				A[k] = tmp[idx1];
				k++;
				idx1++;
			}
		}
		while (idx1 <= m) {
			A[k] = tmp[idx1];
			k++;
			idx1++;
		}
		while (idx2 <= e) {
			A[k] = tmp[idx2];
			k++;
			idx2++;
		}
	}
}
