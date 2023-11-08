package Chapter3;

import java.util.Arrays;
import java.util.Scanner;

public class Jumong_1940 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int A[] = new int[N];
		
		for(int i = 0; i < N; i++) {
			A[i] = sc.nextInt();
		}
		
		Arrays.sort(A);
		
		int start_idx = 0;
		int end_idx = N-1;
		int cnt = 0;
		
		while(start_idx < end_idx) {
			if(A[start_idx] + A[end_idx] == M) {
				cnt++;
				start_idx++;
			}
			else if(A[start_idx] + A[end_idx] < M) {
				start_idx++;
			}
			else {
				end_idx--;
			}
		}
		
		System.out.println(cnt);
	}
}
