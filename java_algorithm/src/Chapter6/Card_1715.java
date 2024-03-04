package Chapter6;

import java.io.*;
import java.util.*;

public class Card_1715 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int i = 0; i < N; i++) {
            int num = sc.nextInt();
            pq.add(num);
        }

        int Sum = 0;
        while(pq.size() != 1) {
            int num1 = pq.remove();
            int num2 = pq.remove();
            Sum += num1 + num2;
            pq.add(num1 + num2);
        }

        System.out.println(Sum);
    }
}