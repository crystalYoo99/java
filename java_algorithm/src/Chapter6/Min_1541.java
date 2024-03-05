package Chapter6;

import java.util.*;
import java.io.*;
public class Min_1541 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        String[] inputArray = input.split("-");
        int[] minusSum = new int[inputArray.length];

        for (int i = 0; i < inputArray.length; i++) {
            String[] temp = inputArray[i].split("\\+");
            for (int j = 0; j < temp.length; j++) {
                minusSum[i] += Integer.parseInt(temp[j]);
            }
        }

        int Sum =

    }
}
