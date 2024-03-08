package Chapter7;

import java.util.Scanner;

public class LeastCommonMultiple_1934 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for (int i = 0; i < T; i++) {
            int A = sc.nextInt();
            int B = sc.nextInt();

            int result = A * B / gcd(A, B);
            System.out.println(result);
        }
    }
    public static int gcd(int A, int B) {
        if (B == 0) return A;
        else return gcd(B, A%B);
    }
}