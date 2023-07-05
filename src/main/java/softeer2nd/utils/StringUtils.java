package softeer2nd.utils;

public class StringUtils {

    // System property: 자바가 실행되는 곳에 대한 정보(현재 위치, 운영체제 정보 등)
    public static final String NEWLINE = System.getProperty("line.separator");

    private StringUtils() {
    }

    public static String appendNewLine(String str) {
        return str + NEWLINE;
    }
}
