package Chapter8;

import java.util.*;
import java.io.*;
public class liar_1043 {
    static int[] parent;
    static int[] Know;
    static ArrayList<Integer>[] party_list;
    static int result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int person = Integer.parseInt(st.nextToken());
        int party =  Integer.parseInt(st.nextToken());
        parent = new int[person+1];
        for (int i = 0; i < person; i++) {
            parent[i] = i;
        }

        // 진실 아는 사람 정보
        st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        Know = new int[T];
        for (int i = 0; i < T; i++) {
            Know[i] = Integer.parseInt(st.nextToken());
        }

        // 파티 정보
        party_list = new ArrayList[party];
        for (int k = 0; k < party; k++) {
            party_list[k] = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            int P = Integer.parseInt(st.nextToken());
            for (int i = 0; i < P; i++) {
                party_list[k].add(Integer.parseInt(st.nextToken()));
            }
        }

        for (int i = 0; i < party; i++) {
            int firstPeople = party_list[i].get(0);
            for (int j = 1; j < party_list[i].size(); j++) {
                union(firstPeople, party_list[i].get(j));
            }
        }

        for (int i = 0; i < party; i++) {
            boolean isPossible = true;
            int curr = party_list[i].get(0);
            for (int j = 0; j < Know.length; j++) {
                if (find(curr) == find(Know[j])) {
                    isPossible = false;
                    break;
                }
            }
            if(isPossible) result++;
        }
        System.out.println(result);
    }
    static public void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a != b) {
            parent[b] = a;
        }
    }

    static public int find(int x) {
        if (parent[x] == x) return x;
        else return parent[x] = find(parent[x]);
    }
}
