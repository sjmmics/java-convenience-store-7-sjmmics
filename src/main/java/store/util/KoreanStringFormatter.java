package store.util;

/** 한국어 및 영어 자리수에 맞추어 정렬된 문자열을 만드는 기능을 담당하는 클래스 */
public final class KoreanStringFormatter {

    private KoreanStringFormatter() {}

    public static String CONVERT_LEFT(String word, int size) {
        String formatter = String.format("%%-%ds", size - getKoreanCount(word));
        return String.format(formatter, word);
    }

    public static String CONVERT_LEFT(int number, int size) {
        String formatter = String.format("%%-,%dd", size);
        return String.format(formatter, number);
    }

    public static String CONVERT_RIGHT(String word, int size) {
        String formatter = String.format("%%%ds", size - getKoreanCount(word));
        return String.format(formatter, word);
    }

    public static String CONVERT_RIGHT(int number, int size) {
        String formatter = String.format("%%,%dd", size);
        return String.format(formatter, number);
    }
    
    public static String CONVERT_RIGHT_WITH_MINUS(int number, int size) {
        String formatter = String.format("%%,%dd", size);
        String line = String.format(formatter, number);
        int lastBlankIndex = line.lastIndexOf(" ");
        return line.substring(0, lastBlankIndex) + "-" + line.substring(lastBlankIndex + 1);
    }

    private static int getKoreanCount(String korean) {
        int count = 0;
        for (int i = 0; i < korean.length() ; i++) {
            if (korean.charAt(i) >= '가' && korean.charAt(i) <= '힣') {
                count++;
            }
        } return count;
    }
    
}
