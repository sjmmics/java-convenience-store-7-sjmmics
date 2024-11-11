package store.domain.discount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.model.discount.Promotion;
import store.model.discount.PromotionDiscountDetail;
import store.model.inventory.Inventory;
import store.model.inventory.Product;
import store.model.inventory.Stock;
import store.model.orderquantity.OrderProductQuantity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PromotionDiscountDetailTest {
    
    private static final String PRODUCT_NAME = "콜라";
    
    private static final int PRICE = 1000;
    
    private static final LocalDate START_DATE = LocalDate.of(2024, 1, 1);
    
    private static final LocalDate END_DATE = LocalDate.of(2024, 12, 31);
    
    private static final LocalDateTime ORDER_TIME_CAN_PROMOTION =
            LocalDateTime.of(2024, 3, 3, 3, 3);
    
    @ParameterizedTest
    @CsvSource(value = {"2,0", "3,1", "4,1", "5,1", "6,2"})
    @DisplayName("프로모션 할인 증정품 수량 테스트")
    void promotionBonusCountTest(int quantity, int expectedBonus) {
        PromotionDiscountDetail discountDetail =
                getOfQuantityAndCondition(quantity, 2);
        
        assertEquals(discountDetail.getBonusCount(), expectedBonus);
    }
    
    @ParameterizedTest
    @CsvSource(value = {"2,2", "1,3"})
    @DisplayName("프로모션 할인 금액 테스트")
    void promotionDiscountAmountTest(int condition, int expectedBonus) {
        PromotionDiscountDetail discountDetail = getOfQuantityAndCondition(6, condition);
        
        assertEquals(discountDetail.getTotalAmountByProduct(), PRICE * expectedBonus);
    }
    
    private PromotionDiscountDetail getOfQuantityAndCondition(int orderQuantity, int condition) {
        
        Inventory inventory = getInventory(condition);
        Product product = inventory.getProduct(PRODUCT_NAME);
        OrderProductQuantity quantity = OrderProductQuantity.init(orderQuantity);
        quantity.setPromotionStockDeductionFirst(0);
        return new PromotionDiscountDetail(product, quantity, inventory);
    }
    
    private Inventory getInventory(int condition) {
        Promotion promotion = new Promotion("2+1", START_DATE, END_DATE, condition);
        Product product = new Product(PRODUCT_NAME, PRICE, promotion);
        Stock stock = new Stock();
        stock.setPromotion(100);
        return new Inventory(Map.of(product, stock));
    }
    
}
