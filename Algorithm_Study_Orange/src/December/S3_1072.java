package December;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.IOException;
import java.lang.Math;

public class S3_1072 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int X = Integer.valueOf(st.nextToken());
        int Y = Integer.valueOf(st.nextToken());
        int Z = (int) Math.floor((double)Y*100.0/X);

        int target = Z==99? -1:(int) Math.ceil((X * Z + X - 100 * Y) / (99 - Z));
        if (target < 0) {
            target = -1;
        }

        for(int i = 0; i <= 1; i++) {
            if ((int) Math.floor((double)(Y+target+i)/(X+target+i)*100.0) > Z) {
                target += i;
                break;
            }
        }

        System.out.println(target);
    }
}