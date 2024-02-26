package Chapter5;

import java.io.*;
import java.util.*;

public class Sosu_2023 {
    static int N;
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        DFS(2, 1);
        DFS(3, 1);
        DFS(5, 1);
        DFS(7, 1);
    }

    static void DFS(int v, int cnt) {
        if (cnt == N) {
            if(isPrime(v)) System.out.println(v);
            return;
        }
        for(int i = 1; i < 10; i+=2) {
            if(isPrime(v*10+i))
                DFS(v*10+i, cnt+1);
        }
    }

    static boolean isPrime(int num) {
        for (int i = 2; i <= num/2; i++) {
            if (num%i == 0) return false;
        }
        return true;
    }
}