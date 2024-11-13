package store.util;

/** 여러 클래스에서 공통으로 쓰는 문자열 검증 기능을 담당하는 클래스 */
public final class StringValidator {

    private StringValidator() {}

    public static boolean IS_EMPTY(String toValidate) {
        return toValidate.isEmpty();
    }
    
    public static boolean CONTAINS_BLANK(String toValidate) {
        return toValidate.contains(" ");
    }

    public static boolean IS_NEITHER_Y_N(String toValidate) {
        return !toValidate.equals("Y") && !toValidate.equals("N");
    }

}
