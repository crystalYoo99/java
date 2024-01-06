package January;

import java.util.Arrays;
import java.util.LinkedList;

public class L2_155651 {
    public static void main(String args[]) {
        String[][] input = {{"15:00", "17:00"}, {"16:40", "18:20"}, {"14:20", "15:20"}, {"14:10", "19:20"}, {"18:20", "21:20"}};
        String[][] input2 = {{"09:10", "10:10"}, {"10:20", "12:20"}};
        int answer = solution(input);
        int answer2 = solution(input2);
        System.out.println(answer + "/" + answer2);
    }
    public static int solution(String[][] book_time) {
        int answer = 0;

        // 시작시간 기준 정렬
        Arrays.sort(book_time,(x, y)-> Integer.parseInt(x[0].substring(0, 2)) == Integer.parseInt(y[0].substring(0, 2))?  Integer.parseInt(x[0].substring(3, 5))-Integer.parseInt(y[0].substring(3, 5)):  Integer.parseInt(x[0].substring(0, 2))-Integer.parseInt(y[0].substring(0, 2)));

        // 첫번째 방에 첫번째 예약 넣기
        LinkedList[] room = new LinkedList[1000];
        int room_cnt = 1;
        room[0] = new LinkedList<Integer>();
        room[0].add(new int[]{Integer.parseInt(book_time[0][0].substring(0, 2))*60 + Integer.parseInt(book_time[0][0].substring(3, 5)), Integer.parseInt(book_time[0][1].substring(0, 2))*60 + Integer.parseInt(book_time[0][1].substring(3, 5))});

        for (int i = 1; i < book_time.length; i++) {
            int start_min = Integer.parseInt(book_time[i][0].substring(0, 2)) * 60 + Integer.parseInt(book_time[i][0].substring(3, 5));
            int end_min = Integer.parseInt(book_time[i][1].substring(0, 2)) * 60 + Integer.parseInt(book_time[i][1].substring(3, 5));
            int flag  = 0;
            for (int j = 0; j < room_cnt; j++) {
                int[] target = (int[]) room[j].getLast();
                if (target[1]  + 10 <= start_min) {
                    flag = 1;
                    room[j].add(new int[]{start_min, end_min});
                    break;
                }
            }
            if (flag == 0) {
                room_cnt += 1;
                room[room_cnt - 1] = new LinkedList<Integer>();
                room[room_cnt - 1].add(new int[]{start_min, end_min});
            }
        }

        answer = room_cnt;
        return answer;
    }
}
