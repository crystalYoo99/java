package Chapter3;

import java.util.Queue;
import java.util.Scanner;
import java.util.LinkedList;

public class Card_2164 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        
        Queue<Integer> que = new LinkedList<>();
        for(int i = 1; i < N+1; i++) {
            que.add(i);
        }
        
        while(que.size() > 1) {
            que.poll();
            int n = que.poll();
            que.add(n);
        }
        System.out.println(que.poll());
    }
}