package Chapter3;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Stack;

public class RightBig_17298 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());
        int[] A = new int[n];
        int[] answer = new int[n];
        StringTokenizer st = new StringTokenizer(bf.readLine());
        
        for(int i = 0; i < n; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }
        
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        
        for (int i = 1; i < n; i++) {
            while(!stack.isEmpty() && A[stack.peek()] < A[i]) {
                answer[stack.pop()] = A[i];
            }
            stack.push(i);
        }
        
        while(!stack.empty()) {
            answer[stack.pop()] = -1;
        }
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for(int i =0; i < n; i++) {
            bw.write(answer[i] + " ");
        }
        bw.write("\n");
        bw.flush();
    }
}