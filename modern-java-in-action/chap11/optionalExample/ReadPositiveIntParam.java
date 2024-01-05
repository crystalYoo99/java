package chap11.optionalExample;

import java.util.Optional;
import java.util.Properties;

import static java.util.Optional.*;

public class ReadPositiveIntParam {
    public static void  main(String[] args) {
        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");

        System.out.println(readDurationImperative(props, "a"));
        System.out.println(readDurationImperative(props, "b"));
        System.out.println(readDurationImperative(props, "c"));
        System.out.println(readDurationImperative(props, "d"));

        System.out.println(readDurationWithOptional(props, "a"));
        System.out.println(readDurationWithOptional(props, "b"));
        System.out.println(readDurationWithOptional(props, "c"));
        System.out.println(readDurationWithOptional(props, "d"));
    }

    // 프로퍼티에서 지속 시간 읽는 명령형 코드
    public static int readDurationImperative(Properties props, String name) {
        String value = props.getProperty(name);
        // 요청한 이름에 해당하는 프로퍼티가 존재하는지 확인
        if (value != null) {
            try {
                int i = Integer.parseInt(value); // 문자열 프로퍼티를 숫자로 변환 시도
                if (i > 0) { // 결과 숫자 양수인지 확인
                    return i;
                }
            } catch (NumberFormatException nfe) { }
        }
        return 0; // 하나라도 조건 실패 시 0 반환
    }

    public static int readDurationWithOptional(Properties props, String name) {
        return ofNullable(props.getProperty(name))
                .flatMap(ReadPositiveIntParam::s2i)
                .filter(i -> i > 0).orElse(0);
    }

    public static Optional<Integer> s2i(String s) {
        try {
            return of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return empty();
        }
    }
}
