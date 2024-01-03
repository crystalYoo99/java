package chap10.stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ErrorReader {
    public static void main(String[] args) throws IOException {
        // 반복 형식으로 로그파일에서 에러행 읽는 코드
        // 같은 의무를 지닌 코드가 여러 행에 분산
        List<String> errors = new ArrayList<>();
        int errorCount = 0;
        String fileName = "error.txt";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();

        while(errorCount < 40 && line != null) {
            if (line.startsWith("ERROR")) {
                errors.add(line);
                errorCount++;
            }
            line = br.readLine();
        }

//        List<String> errorsStream = Files.lines(Paths.get(fileName))
//                .filter(line -> line.startsWith("ERROR"))
//                .limit(40).collect(toList());
    }


}
