package store.model.orderquantity;

import java.util.ArrayList;
import java.util.List;

/**
 * 상품별 주문 상품 수량을 저장하는 클래스
 * 수량 정보는 총 구매수량, 프로모션 재고 차감 수량, 일반 재고 차감 수량으로 이루어진다.
 */
public class OrderProductQuantity {

    private final static int QUANTITY_LENGTH = 3;

    private final static int TOTAL_INDEX = 0;

    private final static int PROMOTION_STOCK_DEDUCTION_INDEX = 1;

    private final static int REGULAR_STOCK_DEDUCTION_INDEX = 2;

    private final List<Integer> quantity;

    public OrderProductQuantity() {
        this.quantity = new ArrayList<>(QUANTITY_LENGTH);
        for (int i = 0; i < QUANTITY_LENGTH; i++) {
            quantity.add(0);
        }
    }
    
    public static OrderProductQuantity init(int totalQuantity) {
        OrderProductQuantity quantity = new OrderProductQuantity();
        quantity.setTotal(totalQuantity);
        return quantity;
    }

    private void setTotal(int amount) {
        quantity.set(TOTAL_INDEX, amount);
    }

    public int getTotal() {
        return quantity.get(TOTAL_INDEX);
    }

    public void setPromotionStockDeductionFirst(int promotionShortage) {
        int totalQuantity = this.getTotal();
        int promotionDeductionAmount = totalQuantity - promotionShortage;
        this.setPromotionStockDeduction(promotionDeductionAmount);
        this.setRegularStockDeduction(promotionShortage);
    }

    private void setPromotionStockDeduction(int count) {
        quantity.set(PROMOTION_STOCK_DEDUCTION_INDEX, count);
    }

    private void setRegularStockDeduction(int regular) {
        quantity.set(REGULAR_STOCK_DEDUCTION_INDEX, regular);
    }

    public void setRegularStockDeductionFirst(int regularStockRemain) {
        int totalQuantity = this.getTotal();
        int shortageRegularStock = totalQuantity - regularStockRemain;
        if (shortageRegularStock > 0) {
            this.setRegularStockDeduction(regularStockRemain);
            this.setPromotionStockDeduction(shortageRegularStock);
            return;
        }
        this.setRegularStockDeduction(totalQuantity);
    }

    public int getRegularStockDeduction() {
        return quantity.get(REGULAR_STOCK_DEDUCTION_INDEX);
    }

    public void plusTotal(int count) {
        int totalQuantity = quantity.get(TOTAL_INDEX);
        quantity.set(TOTAL_INDEX, totalQuantity + count);
    }

    public void minusTotal(int count) {
        int totalQuantity = quantity.get(TOTAL_INDEX);
        quantity.set(TOTAL_INDEX, totalQuantity - count);
    }

    public int getPromotion() {
        return quantity.get(PROMOTION_STOCK_DEDUCTION_INDEX);
    }

    public int getRegular() {
        return quantity.get(REGULAR_STOCK_DEDUCTION_INDEX);
    }

}
