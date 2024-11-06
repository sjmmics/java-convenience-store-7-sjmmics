package store.util;

import java.text.DecimalFormat;

public final class Constants {
    
    private Constants() {}
    
    public static String EXCEPTION_MESSAGE_PREFIX = "[ERROR] ";
    
    public static DecimalFormat THOUSAND_COMMA = new DecimalFormat("###,###");
    
    public static final String EMPTY_INPUT_EXCEPTION_MESSAGE =
            Constants.EXCEPTION_MESSAGE_PREFIX +
                    "아무 것도 입력하지 않으셨습니다. 다시 입력해주세요.";
    
    public static final String DEFAULT_EXCEPTION_MESSAGE =
            Constants.EXCEPTION_MESSAGE_PREFIX +
                    "잘못된 입력입니다. 다시 입력해주세요.";
    
    public static final String EXCEPTION_MESSAGE_WRONG_FORMAT =
            Constants.EXCEPTION_MESSAGE_PREFIX +
                    "올바르지 않은 형식입니다. 다시 입력해주세요.";
    


}
