package Chapter3;

import java.util.*;
import java.io.*;

public class NumSum_11720 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		String s = sc.next();
		char[] c = s.toCharArray();
		int sum = 0;
		
		for (int i = 0; i < num; i++) {
			sum += c[i] - '0';
		}
		
		System.out.println(sum);
	}

}
