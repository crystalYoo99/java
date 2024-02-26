package Chapter4;

import java.io.*;
import java.util.*;

public class Radix_10989 {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());
        int[] A = new int[N];
        int[] tmp = new int[N];


        for(int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(br.readLine());
            tmp[i] = A[i];
        }
        br.close();

//        //radix
//        Queue<Integer>[] que = new LinkedList[10];
//        for (int i = 0; i <= 9; i++ ){
//            que[i] = new LinkedList<Integer>();
//        }
//        for (int i = 1; i <= 10000; i*=10) {
//            for (int j = 0; j < N; j++) {
//                tmp[j] = A[j];
//                que[tmp[j]/i%10].add(tmp[j]);
//            }
//            int cnt = 0;
//            for (int j = 0; j < 10; j++) {
//                while(!que[j].isEmpty()) {
//                    A[cnt] = que[j].poll();
//                    cnt ++;
//                }
//            }
//        }

        // radix2
        int[] queue = new int[10];
        int[] s_queue = new int[10];
        int jarisu = 1;

        while(jarisu <= 10000) {
            // 자리수 합
            for (int i = 0; i < N; i++) {
                queue[A[i]/jarisu%10] += 1;
            }
            // 자리수 합의 합배열
            s_queue[0] = 1;
            for(int i = 1; i < 10; i++) {
                s_queue[i] = s_queue[i-1] + queue[i-1];
            }
            // 자리수 인덱스대로 재정렬
            for(int i = 0; i < N; i++) {
                int target = A[i]/jarisu%10;
                tmp[s_queue[target]-1] = A[i];
                s_queue[target] += 1;
            }

            for(int i = 0; i < 10; i++) {
                queue[i] = 0;
                s_queue[i] = 0;
            }

            System.arraycopy(tmp, 0, A, 0, N);
            jarisu *= 10;
        }

        for(int i = 0; i < N; i++) {
            bw.write(A[i] + "\n");
        }
        bw.flush();
        bw.close();
    }
}