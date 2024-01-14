package January;

import java.util.*;

public class L2_138476 {
    public static void main(String[] args) {
        Map<Integer, Integer> t = new HashMap<>();
        int answer = 0;

        int k = 6;
        int arr[] = {1, 3, 2, 5, 4, 5, 2, 3};

        for (int i = 0; i < arr.length; i++) {
            if (t.get(arr[i]) == null) {
                t.put(arr[i], 1);
            }
            else {
                t.put(arr[i], t.get(arr[i])+1);
            }
        }

        List<Integer> keySet = new ArrayList<>(t.keySet());
        keySet.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return t.get(o2).compareTo(t.get(o1));
            }
        });

        int total = 0;
        while(true) {
            if (k <= total) break;
            total += t.get(keySet.get(answer));
            answer += 1;
        }
        System.out.println(t);
        System.out.println(answer);
        //return answer; //3
    }
}
