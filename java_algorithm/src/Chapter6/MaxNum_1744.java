package Chapter6;
import java.util.*;
import java.io.*;

public class MaxNum_1744 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        PriorityQueue<Integer> pq_plus = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> pq_minus = new PriorityQueue<>();
        int zero_cnt = 0;
        int one_cnt  = 0;

        for (int i = 0; i < N; i++) {
            int num = sc.nextInt();
            if (num > 1) pq_plus.add(num);
            else if(num == 1) one_cnt++;
            else if(num == 0) zero_cnt++;
            else pq_minus.add(num);
        }

        int Sum = 0;
        while(pq_plus.size() > 1) {
            int num1 = pq_plus.remove();
            int num2 = pq_plus.remove();
            Sum += num1 * num2;
        }
        if(pq_plus.size() == 1) {
            Sum += pq_plus.remove();
        }
        while(pq_minus.size() > 1) {
            int num1 = pq_minus.remove();
            int num2 = pq_minus.remove();
            Sum += num1 * num2;
        }
        if(pq_minus.size() == 1 && zero_cnt == 0) {
            Sum += pq_minus.remove();
        }

        Sum += one_cnt;
        System.out.println(Sum);
    }
}
