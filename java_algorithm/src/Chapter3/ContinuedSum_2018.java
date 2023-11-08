package Chapter3;

import java.util.Scanner;

public class ContinuedSum_2018 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int sum = 1;
		int cnt = 1;
		int start_idx = 1;
		int end_idx = 1;
		
		while(end_idx != N) {
			if (sum == N) {
				cnt++;
				end_idx++;
				sum += end_idx;
			}
			else if(sum > N) {
				sum -= start_idx;
				start_idx++;
			}
			else {
				end_idx ++;
				sum += end_idx;
			}
		}
		
		
		System.out.println(cnt);
	}

}
