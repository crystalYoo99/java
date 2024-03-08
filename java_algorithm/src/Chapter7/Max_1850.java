package Chapter7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Max_1850 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        long A = sc.nextLong();
        long B = sc.nextLong();
        long result = gcd(A, B);

        while (result > 0) {
            bw.write("1");
            result--;
        }
        bw.flush();
        bw.close();
    }
    public static long gcd(long a, long b) {
        if (b == 0) return a;
        else return gcd(b, a%b);
    }
}