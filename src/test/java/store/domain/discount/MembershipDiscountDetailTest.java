package store.domain.discount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.model.discount.MembershipDiscountDetail;
import store.model.inventory.Inventory;
import store.model.inventory.Product;
import store.model.inventory.Stock;
import store.model.order.OrderDetails;
import store.model.order.OrderDetailsFactory;
import store.model.orderquantity.OrderQuantityAdjuster;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MembershipDiscountDetailTest {
    
    private OrderDetailsFactory factory;
    
    private Inventory inventory;
    
    private static final int WATER_PRICE = 500;
    
    private static final Product WATER = new Product("물", WATER_PRICE, null);
    
    private static final int TOTAL_WATER_STOCK = 5000;
    
    @BeforeEach
    void setUp() {
        this.inventory = setUpInventory();
        this.factory = new OrderDetailsFactory();
    }
    
    private Inventory setUpInventory() {
        Stock waterStock = new Stock();
        waterStock.setRegular(TOTAL_WATER_STOCK);
        Map<Product, Stock> productStockMap = Map.of(WATER, waterStock);
        return new Inventory(productStockMap);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5"})
    @DisplayName("멤버십 할인 금액 계산 테스트")
    void membershipDiscountAmountTest(int quantity) {
        MembershipDiscountDetail discountDetail =
                getMemberShipDiscountDetailFromWaterOrderQuantity(quantity);
        assertEquals(discountDetail.getAmount(),
                (int) (WATER_PRICE * quantity * MembershipDiscountDetail.DISCOUNT_RATIO));
        
    }
    
    @Test
    @DisplayName("멤버십 할인 금액 한도 계산 테스트")
    void membershipDiscountAmountMaxLimitTest() {
        MembershipDiscountDetail discountDetail =
                getMemberShipDiscountDetailFromWaterOrderQuantity(2000);
        assertEquals(discountDetail.getAmount(), MembershipDiscountDetail.MAX_DISCOUNT_AMOUNT);
        assertNotEquals(discountDetail.getAmount(),
                (int) (WATER_PRICE * 2000 * MembershipDiscountDetail.DISCOUNT_RATIO));
        
    }
    
    MembershipDiscountDetail getMemberShipDiscountDetailFromWaterOrderQuantity(int waterQuantity) {
        LocalDateTime orderTime = LocalDateTime.of(2024, 3, 3, 10, 50);
        OrderDetails orderDetails = factory.create("[물-" + waterQuantity + "]", inventory);
        OrderQuantityAdjuster adjuster = new OrderQuantityAdjuster(orderDetails);
        adjuster.applyOfOrderDetailsAndInventory(inventory, orderTime);
        return new MembershipDiscountDetail(orderDetails);
    }
    
}
