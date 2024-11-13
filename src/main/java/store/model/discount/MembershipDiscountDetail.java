package store.model.discount;

import store.model.inventory.Product;
import store.model.order.OrderDetails;
import store.model.orderquantity.OrderProductQuantity;

import java.util.Map;

/** 주문에 관한 멤버십 할인 정보를 저장하는 클래스 */
public class MembershipDiscountDetail {

    public final static double DISCOUNT_RATIO = 0.3;

    public final static int MAX_DISCOUNT_AMOUNT = 8_000;

    private final int totalAmount;

    public MembershipDiscountDetail() {
        this.totalAmount = 0;
    }

    public MembershipDiscountDetail(OrderDetails details) {
        int totalNonPromotionAmount = details.getEntrySet().stream()
                .mapToInt(MembershipDiscountDetail::getTotalNonPromotionAmount)
                .sum();
        int memberShipDiscount = (int) (totalNonPromotionAmount * DISCOUNT_RATIO);
        this.totalAmount = Math.min(MAX_DISCOUNT_AMOUNT, memberShipDiscount);
    }

    private static int getTotalNonPromotionAmount(Map.Entry<Product, OrderProductQuantity> entry) {
        Product product = entry.getKey();
        OrderProductQuantity quantity = entry.getValue();
        int unitPrice = product.getUnitPrice();
        int regularStockDeductionAmount = quantity.getRegularStockDeduction();
        return regularStockDeductionAmount * unitPrice;
    }

    public int getAmount() {
        return this.totalAmount;
    }

}
