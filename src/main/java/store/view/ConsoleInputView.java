package store.view;

import camp.nextstep.edu.missionutils.Console;

public class ConsoleInputView implements InputView {
    
    private static final String orderGuideMessage =
            "구매하실 상품명과 수량을 입력해 주세요. " +
                    "(예: [사이다-2],[감자칩-1]";
    
    @Override
    public String getOrder() {
        System.out.println(orderGuideMessage);
        return Console.readLine();
    }
}
