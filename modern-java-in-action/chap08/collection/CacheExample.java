package chap08.collection;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CacheExample {
    private MessageDigest messageDigest;

    public static void main(String[] args) {
        new CacheExample().main();
    }

    public CacheExample() {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    private void main() {
        List<String> lines = Arrays.asList(
                " Nel   mezzo del cammin  di nostra  vita ",
                "mi  ritrovai in una  selva oscura",
                " che la  dritta via era   smarrita "
        );
        Map<String, byte[]> dataToHash = new HashMap<>();

        // line은 맵에서 찾을 키, 키가 존재하지 않으면 동작 실행
        lines.forEach(line -> dataToHash.computeIfAbsent(line, this::calculateDigest));

        dataToHash.forEach((line, hash) ->
                System.out.printf("%s -> %s%n", line, new String(hash).chars().map(i -> i & 0xff).mapToObj(String::valueOf).collect(Collectors.joining(", ", "[", "]"))));
    }

    // 헬퍼가 제공된 키의 해시를 계산
    private byte[] calculateDigest(String key) {
        return messageDigest.digest(key.getBytes(StandardCharsets.UTF_8));
    }
}
