package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.orderquantity.OrderProductQuantity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OrderProductQuantityTest {
    
    @Test
    @DisplayName("수량 초기화 테스트")
    void initTest() {
        OrderProductQuantity quantity = OrderProductQuantity.init(10);
        assertEquals(quantity.getTotal(), 10);
        assertNotEquals(quantity.getPromotion(), 10);
        assertNotEquals(quantity.getRegular(), 10);
    }
    
    @Test
    @DisplayName("총 수량 감소 테스트")
    void totalQuantityMinusTest() {
        OrderProductQuantity quantity = OrderProductQuantity.init(10);
        quantity.minusTotal(1);
        assertEquals(quantity.getTotal(), 10 - 1);
        assertNotEquals(quantity.getTotal(), 10);
    }
    
    @Test
    @DisplayName("총 수량 증가 테스트")
    void totalQuantityPlusTest() {
        OrderProductQuantity quantity = OrderProductQuantity.init(10);
        quantity.plusTotal(3);
        assertEquals(quantity.getTotal(), 10 + 3);
        assertNotEquals(quantity.getTotal(), 10);
    }
    
    @Test
    @DisplayName("일반 재고 우선 차감 테스트")
    void deductRegularStockFirstTest() {
        int totalQuantity = 10;
        OrderProductQuantity quantity = OrderProductQuantity.init(totalQuantity);
        int regularStockRemain = 3;
        quantity.setRegularStockDeductionFirst(regularStockRemain);
        assertEquals(quantity.getTotal(), totalQuantity);
        assertEquals(quantity.getRegular(), regularStockRemain);
        assertEquals(quantity.getPromotion(), totalQuantity - regularStockRemain);
    }
    
    @Test
    @DisplayName("프로모션 재고 우선 차감 테스트")
    void deductPromotionStockFirstTest() {
        int totalQuantity = 10;
        OrderProductQuantity quantity = OrderProductQuantity.init(totalQuantity);
        int promotionStockShortage = 3;
        quantity.setPromotionStockDeductionFirst(3);
        assertEquals(quantity.getPromotion(), totalQuantity - promotionStockShortage);
        assertEquals(quantity.getRegular(), promotionStockShortage);
        assertEquals(quantity.getTotal(), 10);
    }
    
}
