package store.view;

import camp.nextstep.edu.missionutils.Console;

import store.model.inventory.Inventory;
import store.model.orderquantity.OrderQuantityOption;

import static store.util.StringValidator.IS_NEITHER_Y_N;

/**
 * 입력과 관련된 안내 메시지를 출력하고 입력을 받아서 StoreController에 전달하는 클래스
 * 입력값이 Y 또는 N이어야 하는 경우 입력받은 문자열을 검증해서 잘못된 형식이면
 * IllegalArgumentException를 호출하고, 예외 메시지를 출력하고 다시 입력을 받는다.
 */
public class InputView {

    public InputView() {}

    public String getOrder(Inventory inventory) {
        System.out.println(MESSAGE.INTRODUCTION);
        System.out.println(inventory);
        System.out.println(MESSAGE.ORDER_GUIDE);
        return Console.readLine();
    }

    public String getOptionDecision(OrderQuantityOption option) {
        System.out.println(option);
        while (true) {
            try {
                return getProperYNInput();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public String getKeepOpenLine() {
        return getYNInputByMessage(MESSAGE.ENTER_KEEP_ORDER);
    }

    public String getMemberShipDiscountDecision() {
        return getYNInputByMessage(MESSAGE.MEMBERSHIP_DISCOUNT_DECISION_GUIDE);
    }

    private String getYNInputByMessage(MESSAGE message) {
        while (true) {
            System.out.println(message);
            try {
                return getProperYNInput();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String getProperYNInput() throws IllegalArgumentException {
        String yesOrNoLine = Console.readLine();
        if (IS_NEITHER_Y_N(yesOrNoLine)) {
            throw new IllegalArgumentException(MESSAGE.EXCEPTION_ENTER_Y_OR_N.toString());
        }
        return yesOrNoLine;
    }

    private enum MESSAGE {

        INTRODUCTION("안녕하세요. w편의점입니다.\n현재 보유하고 있는 상품입니다.\n"),

        ORDER_GUIDE("구매하실 상품명과 수량을 입력해 주세요. " +
                "(예: [사이다-2],[감자칩-1]"),

        ENTER_KEEP_ORDER("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"),
        
        EXCEPTION_ENTER_Y_OR_N("잘못된 입력입니다. " + "\"Y\", \"N\" 중 하나를 입력하세요."),

        MEMBERSHIP_DISCOUNT_DECISION_GUIDE("멤버십 할인을 받으시겠습니까? (Y/N)");

        final String message;

        MESSAGE(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return this.message;
        }
    }

}
