package store.model.orderquantity;

import store.model.inventory.Inventory;
import store.model.order.OrderDetails;
import store.model.inventory.Product;

import java.time.LocalDateTime;
import java.util.Map;

import static store.model.orderquantity.OrderQuantityOption.OptionCase;
import static store.model.orderquantity.OrderQuantityOption.OptionCase.NOT_CONTAIN_BONUS;
import static store.model.orderquantity.OrderQuantityOption.OptionCase.OUT_OF_PROMOTION_STOCK;
import static store.util.Constants.BONUS_COUNT;

/**
 * 상품 주문 수량의 세부사항을 조정하는 클래스
 * 재고 및 프로모션 할인에 따른 주문 수량 변경 선택에 의해 수량(OrderProductQuantity)를 조정,
 * 재고 차감 여부를 결정하기 위해 프로모션, 일반 재고 중 어느 부분을 차감할 것인지 결정한다.
 */
public class OrderQuantityAdjuster {

    private final OrderDetails orderDetails;

    public OrderQuantityAdjuster(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void applyOfOrderDetailsAndQuantityOptions(OrderQuantityOptions options) {
        for (OrderQuantityOption option : options) {
            Product product = option.getProduct();
            OptionCase optionCase = option.getOptionCase();
            if (optionCase.isEquals(NOT_CONTAIN_BONUS) && option.isDecisionYes()) {
                orderDetails.adjustQuantityNotContainsBonus(product, BONUS_COUNT);
            }
            if (optionCase.isEquals(OUT_OF_PROMOTION_STOCK) && !option.isDecisionYes()) {
                orderDetails.adjustQuantityDoesNotBuyRegular(product, option.getShortage());
            }
        }
    }

    public void applyOfOrderDetailsAndInventory(Inventory inventory, LocalDateTime orderTime) {
        for (Map.Entry<Product, OrderProductQuantity> entry : orderDetails.getEntrySet()) {
            setStockByCase(entry, inventory, orderTime);
        }
    }

    private void setStockByCase(Map.Entry<Product, OrderProductQuantity> entry,
                                Inventory inventory, LocalDateTime orderTime) {
        OrderProductQuantity quantity = entry.getValue();
        int totalQuantity = quantity.getTotal();
        if (canNotApplyPromotionDiscount(entry, inventory, orderTime)) {
            quantity.setRegularStockDeductionFirst(totalQuantity);
            return;
        }
        Product product = entry.getKey();
        int promotionStockShortage = inventory.getPromotionStockShortage(product, totalQuantity);
        quantity.setPromotionStockDeductionFirst(promotionStockShortage);
    }

    private boolean canNotApplyPromotionDiscount(Map.Entry<Product, OrderProductQuantity> entry,
                                                 Inventory inventory,
                                                 LocalDateTime orderTime) {
        Product product = entry.getKey();
        if (!product.isPromotionPeriod(orderTime)) {
            return true;
        }
        int promotionStock = inventory.getPromotionStock(product);
        return promotionStock == 0;
    }

}
