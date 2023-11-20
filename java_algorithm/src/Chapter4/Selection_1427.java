package Chapter4;

import java.util.Scanner;

public class Selection_1427 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		char[] N = sc.next().toCharArray();
		int index = 0;
		
		while(index < N.length) {
			int max = (int)N[index];
			int max_idx = index;
			for (int i = index ; i < N.length; i++) {
				if (max < (int)N[i]) {
					max = (int)N[i];
					max_idx = i;
				}
			}
			char temp = N[max_idx];
			N[max_idx] = N[index];
			N[index] = temp;
			index++;
		}
		
		System.out.println(N);
		
	}

}
