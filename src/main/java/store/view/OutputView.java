package store.view;

import store.model.order.Receipt;

/** StoreController로부터 전달받은 결과값을 출력하는 클래스 */
public class OutputView {

    public void printReceipt(Receipt receipt) {
        System.out.println(receipt);
    }

}
