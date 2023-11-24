package Chapter4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Bubble2_1571 {
	public static int N;
	public static int A[], tmp[];
	public static long answer;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.valueOf(br.readLine());
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		A =  new int[N+1];
		tmp = new int[N+1];
		
		for(int i = 1; i <= N; i++) {
			A[i] = Integer.valueOf(st.nextToken());
		}
		merge(1, N);
		
		System.out.println(answer);
	}
	
	public static void merge(int s, int e) {
		
	}
}
