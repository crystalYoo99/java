package Chapter3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DNA_12891 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int S = Integer.valueOf(st.nextToken());
		int P = Integer.valueOf(st.nextToken());
		
		char D[] = new char[S];
		D = br.readLine().toCharArray();
		
		st  = new StringTokenizer(br.readLine());
		int A = Integer.valueOf(st.nextToken());
		int C = Integer.valueOf(st.nextToken());
		int G = Integer.valueOf(st.nextToken());
		int T = Integer.valueOf(st.nextToken());
		
		int cnt = 0;
		int cnt_A= 0, cnt_C = 0, cnt_G = 0, cnt_T = 0;
		for(int i = 0; i < P; i++) {
			if (D[i] == 'A') cnt_A ++;
			else if(D[i] == 'C') cnt_C ++;
			else if(D[i] == 'G') cnt_G ++;
			else cnt_T ++;
		}
		
		if (cnt_A >= A && cnt_C >= C && cnt_G >= G && cnt_T >= T)
			cnt ++;
		
		for(int i = P; i < S; i++) {
			if (D[i] == 'A') cnt_A ++;
			else if(D[i] == 'C') cnt_C ++;
			else if(D[i] == 'G') cnt_G ++;
			else cnt_T ++;
			
			if (D[i-P] == 'A') cnt_A --;
			else if(D[i-P] == 'C') cnt_C --;
			else if(D[i-P] == 'G') cnt_G --;
			else cnt_T --;
			
			if (cnt_A >= A && cnt_C >= C && cnt_G >= G && cnt_T >= T)
				cnt ++;
		}
		System.out.println(cnt);
	}
}
