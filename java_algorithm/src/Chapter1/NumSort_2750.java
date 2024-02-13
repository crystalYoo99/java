package Chapter1;

import java.util.*;
import java.io.*;

public class NumSort_2750 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		int[] A = new int[num];
		
		for(int i = 0; i < num; i++) {
			A[i] = sc.nextInt();
		}
		Arrays.sort(A);
		
		for(int n : A) {
			System.out.println(n);
		}
	}
}
