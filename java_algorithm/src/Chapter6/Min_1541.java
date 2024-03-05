package Chapter6;

import java.util.*;
import java.io.*;


public class Min_1541 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        String[] A = s.split("-");
        int Sum = 0;

        for (int i = 0; i < A.length; i++) {
            int temp_sum = 0;
            String[] temp = A[i].split("\\+");
            for (int j = 0; j < temp.length; j++) {
                temp_sum += Integer.parseInt(temp[j]);
            }
            if (i == 0) Sum += temp_sum;
            else Sum -= temp_sum;
        }
        System.out.println(Sum);
    }
}