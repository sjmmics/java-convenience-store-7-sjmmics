package store.util;

/** 예외 호출 메시지를 저장한 클래스 */
public enum ExceptionMessage {

    WRONG_FILE("잘못된 파일입니다."),

    EMPTY_INPUT("아무 것도 입력하지 않으셨습니다. 다시 입력해주세요."),

    CONTAINS_BLANK("빈 칸(띄어쓰기) 없이 다시 입력해주세요."),

    OUT_OF_INT("숫자는 2의 31 제곱보다 작아야 합니다. 다시 입력해주세요."),

    DO_NOT_HAVE_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),

    EXCEED_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),

    BUY_ZERO_QUANTITY("주문은 1개 이상이어야 합니다. 다시 입력해주세요."),

    DUPLICATE_NAMES("주문 상품 별 수량을 나누어서 입력하면 안됩니다. 다시 입력해주세요."),

    WRONG_FORMAT("올바르지 않은 형식입니다. 다시 입력해주세요.");

    final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return Constants.EXCEPTION_MESSAGE_PREFIX + message;
    }
}
