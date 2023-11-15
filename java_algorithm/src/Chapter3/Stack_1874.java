package Chapter3;

import java.util.Scanner;
import java.util.Stack;

public class Stack_1874 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		Stack<Integer> stack = new Stack<>();
		StringBuffer bf = new StringBuffer();
		int num = 1;
		boolean result = true;
		
		for (int i = 0; i < N; i++) {
			int curr = sc.nextInt();
			if (curr >= num) {
				while(curr >= num) {
					stack.push(num++);
					bf.append("+\n");
				}
				stack.pop();
				bf.append("-\n");
			}
			else {
				int n = stack.pop();
				if(n> curr) {
					System.out.println("NO");
					result = false;
					break;
				}
				else {
					bf.append("-\n");
				}
			}
		}

		if(result)
			System.out.println(bf.toString());
	}
}
