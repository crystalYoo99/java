package November;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class S4_1158 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int N = sc.nextInt();
		int K = sc.nextInt();
		
		int A[] = new int[N];
		int cnt = 0;
		int idx = 0;
		
		for(int i = 0; i < N; i++) {
			A[i] = i+1;
		}
		
		bw.write('<');
		int temp_cnt = 1;
		while (cnt < N) {
			while (temp_cnt < K) {
				if (idx+1 < N) {
					idx++;
				}
				else {
					idx = 0;
				}
				if(A[idx] > 0) temp_cnt++;
			}
			bw.write(String.valueOf(A[idx]));
			A[idx] = 0;
			cnt ++;
			if (cnt < N) bw.write(", ");
			temp_cnt = 0;
		}
		
		bw.write(">");
		bw.flush();
	}
}
