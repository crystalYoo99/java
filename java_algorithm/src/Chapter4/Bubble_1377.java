package Chapter4;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.io.IOException;

public class Bubble_1377 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        mData str[] = new mData[N];
        
        for (int i =0; i < N; i++) {
            str[i] = new mData(Integer.parseInt(br.readLine()), i);
        }
        
        Arrays.sort(str);
        int max = 0;
        for (int i =0; i < N; i++) {
            if (max < str[       i].index - i) {
                max = str[i].index-i;
            }
        }
        System.out.println(max + 1);
    }
}
class mData implements Comparable<mData> {
	int value;
	int index;
	
	public mData(int value, int index) {
		super();
		this.value = value;
		this.index = index;
	}
        
    @Override
    public int compareTo(mData o) {
        return this.value - o.value;
    }
}