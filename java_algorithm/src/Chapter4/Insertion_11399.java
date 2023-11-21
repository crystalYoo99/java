package Chapter4;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Insertion_11399 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.valueOf(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int A[] = new int[N];
		
		for(int i = 0; i < N; i++) {
			A[i] = Integer.valueOf(st.nextToken());
		}
		
		for(int i = 1; i < N; i++) {
			int idx = i;
			int value  = A[i];
			
			for(int j = i-1; j >= 0; j--) {
				if (A[i] > A[j]) {
					idx = j+1;
					break;
				}
				if(j == 0) {
					idx  = 0;
				}
			}
			
			for(int j = i; j > idx; j--) {
				A[j] = A[j-1];
			}
			A[idx] = value;
		}
		
		
		int S[] = new int[N];
		S[0] = A[0];
		int answer = S[0];
		for(int i = 1; i < N; i++) {
			S[i] = S[i-1] + A[i];
			answer += S[i];
		}
		
		System.out.println(answer);
	}

}
