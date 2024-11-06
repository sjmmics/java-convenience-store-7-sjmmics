package store.util;

public final class StringValidator {
    

    public static boolean isEmpty(String toValidate) {
        return toValidate.isEmpty();
    }
    
    public static boolean containsBlank(String toValidate) {
        return toValidate.contains(" ");
    }
}
