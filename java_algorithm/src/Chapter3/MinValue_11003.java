package Chapter3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class MinValue_11003 {
	static class Node {
		public int value;
		public int index;

		Node(int value, int index) {
			this.value = value;
			this.index = index;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.valueOf(st.nextToken());
		int L = Integer.valueOf(st.nextToken());
		Deque<Node> deq = new LinkedList<>();
		
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			int curr = Integer.valueOf(st.nextToken());
			
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
}
