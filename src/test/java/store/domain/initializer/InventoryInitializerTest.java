package store.domain.initializer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.inventory.Inventory;
import store.model.inventory.Product;
import store.model.discount.Promotion;
import store.model.inventory.Promotions;
import store.util.InventoryInitializer;
import store.util.PromotionsInitializer;

import static org.junit.jupiter.api.Assertions.*;
import static store.domain.initializer.PromotionInitializerTest.TEST_PROMOTION_FILE_PATH;

public class InventoryInitializerTest {
    
    public static final String TEST_INVENTORY_FILE_PATH = "src/test/java/resources/test_products.md";
    private PromotionsInitializer promotionInitializer;
    private Promotions promotions;
    private InventoryInitializer initializer;
    private Inventory inventory;
    private Promotion promotionStarkingDrink;
    private Product cola;
    
    @BeforeEach
    void setUp() {
        this.promotionInitializer = new PromotionsInitializer();
        this.promotions = promotionInitializer.get(TEST_PROMOTION_FILE_PATH);
        this.initializer = new InventoryInitializer();
        this.inventory = initializer.get(promotions, TEST_INVENTORY_FILE_PATH);
        this.promotionStarkingDrink = promotions.getFromName("콜라");
        this.cola = new Product("콜라", 1000, promotionStarkingDrink);
    }
    
    @Test
    @DisplayName("없는 상품 확인 테스트")
    void notContainsProductTest() {
        assertTrue(inventory.doNotContainsProduct("사이다"));
    }
    
    @Test
    @DisplayName("인벤토리 상품 불러오기 테스트")
    void getProductTest() {
        Product saveProduct = inventory.getProduct("콜라");
        assertEquals(saveProduct.getName(), cola.getName());
    }
    
    @Test
    @DisplayName("프로모션 재고 확인 테스트")
    void promotionStockTest() {
        int promotionStock = inventory.getPromotionStock(cola);
        assertEquals(promotionStock, 10);
        assertNotEquals(promotionStock, 8);
    }

}
