package Chapter3;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Deque;
import java.util.LinkedList;

public class MinValue_11003 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int N = sc.nextInt();
		int L = sc.nextInt();
		Deque<Node> deq = new LinkedList<>();
		
		for (int i = 0; i < N; i++) {
			int curr = sc.nextInt();
			
			while(!deq.isEmpty() && deq.getLast().value > curr) {
				deq.removeLast();
			}
			deq.addLast(new Node(curr, i));
			
			if(deq.getFirst().index <= i-L) {
				deq.removeFirst();
			}
			bw.write(deq.getFirst().value + " ");
		}
		bw.flush();
		bw.close();
	}
	
	static class Node {
		public int value;
		public int index;
		
		Node(int value, int index) {
			this.value = value;
			this.index = index;
		}
	}

}
