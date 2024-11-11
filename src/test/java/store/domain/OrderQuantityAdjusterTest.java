package store.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.inventory.Product;
import store.model.order.OrderDetails;
import store.model.orderquantity.OrderProductQuantity;
import store.model.orderquantity.OrderQuantityAdjuster;
import store.model.orderquantity.OrderQuantityOption;
import store.model.orderquantity.OrderQuantityOptions;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static store.model.orderquantity.OrderQuantityOption.OptionCase.NOT_CONTAIN_BONUS;
import static store.model.orderquantity.OrderQuantityOption.OptionCase.OUT_OF_PROMOTION_STOCK;

public class OrderQuantityAdjusterTest {
    
    private static final int ORIGINAL_TOTAL_QUANTITY = 10;
    
    private static final Product PRODUCT = new Product("콜라", 1000, null);
    
    private OrderProductQuantity quantity;
    
    private static final int PROMOTION_STOCK_SHORTAGE = 3;
    
    private OrderDetails orderDetails;
    
    @BeforeEach
    void setQuantity() {
        this.quantity = OrderProductQuantity.init(ORIGINAL_TOTAL_QUANTITY);
    }

    @Test
    @DisplayName("1개 추가하면 증정품 제공하는데 추가 주문하는 테스트")
    void promotionBonusGetTest() {
        setAdjusterOfCaseAndDecision(NOT_CONTAIN_BONUS, true, 1);
        for (Map.Entry<Product, OrderProductQuantity> entry : orderDetails.getEntrySet()) {
            OrderProductQuantity quantity = entry.getValue();
            assertEquals(quantity.getTotal(), ORIGINAL_TOTAL_QUANTITY + 1);
        }
    }
    
    @Test
    @DisplayName("1개 추가하면 증정품 제공하는데 추가 주문하지 않는 테스트")
    void promotionBonusNotGetTest() {
        setAdjusterOfCaseAndDecision(NOT_CONTAIN_BONUS, false, 1);
        for (Map.Entry<Product, OrderProductQuantity> entry : orderDetails.getEntrySet()) {
            OrderProductQuantity quantity = entry.getValue();
            assertEquals(quantity.getTotal(), ORIGINAL_TOTAL_QUANTITY);
            assertNotEquals(quantity.getTotal(), ORIGINAL_TOTAL_QUANTITY + 1);
        }
    }
    
    @Test
    @DisplayName("프로모션 증정품 재고 없어서 주문 수량 줄이는 테스트")
    void outOfPromotionStockReduceQuantity() {
        setAdjusterOfCaseAndDecision(OUT_OF_PROMOTION_STOCK, false, PROMOTION_STOCK_SHORTAGE);
        for (Map.Entry<Product, OrderProductQuantity> entry : orderDetails.getEntrySet()) {
            OrderProductQuantity quantity = entry.getValue();
            assertEquals(quantity.getTotal(), ORIGINAL_TOTAL_QUANTITY - PROMOTION_STOCK_SHORTAGE);
            assertNotEquals(quantity.getTotal(), ORIGINAL_TOTAL_QUANTITY);
        }
    }
    
    @Test
    @DisplayName("프로모션 증정품 재고 없어서 주문 수량 안 줄이는 테스트")
    void outOfPromotionStockNotReduceQuantity() {
        setAdjusterOfCaseAndDecision(OUT_OF_PROMOTION_STOCK, true, PROMOTION_STOCK_SHORTAGE);
        for (Map.Entry<Product, OrderProductQuantity> entry : orderDetails.getEntrySet()) {
            OrderProductQuantity quantity = entry.getValue();
            assertEquals(quantity.getTotal(), ORIGINAL_TOTAL_QUANTITY);
            assertNotEquals(quantity.getTotal(), ORIGINAL_TOTAL_QUANTITY - PROMOTION_STOCK_SHORTAGE);
        }
    }
    
    void setAdjusterOfCaseAndDecision(OrderQuantityOption.OptionCase optionCase,
                                      boolean decision, int shortage) {
        
        OrderQuantityOption option = new OrderQuantityOption(PRODUCT, optionCase, shortage);
        if (decision) {
            option.setDecisionTrue();
        }
        OrderQuantityOptions options = new OrderQuantityOptions(List.of(option));
        this.orderDetails = new OrderDetails(Map.of(PRODUCT, quantity));
        OrderQuantityAdjuster adjuster = new OrderQuantityAdjuster(orderDetails);
        adjuster.applyOfOrderDetailsAndQuantityOptions(options);
    }
    
}