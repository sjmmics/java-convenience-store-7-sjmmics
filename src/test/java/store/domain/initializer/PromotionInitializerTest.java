package store.domain.initializer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.discount.Promotion;
import store.model.inventory.Promotions;
import store.util.PromotionsInitializer;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PromotionInitializerTest {
    
    public static final String TEST_PROMOTION_FILE_PATH = "src/test/java/resources/test_promotions.md";
    
    @Test
    @DisplayName("프로모션 불러오기 테스트")
    void getInitializedPromotionTest() {
        PromotionsInitializer initializer = new PromotionsInitializer();
        Promotions promotions = initializer.get(TEST_PROMOTION_FILE_PATH);
        
        Promotion promotion = promotions.getFromName("탄산2+1");
        assertEquals(promotion.getPromotionCondition(), 2);
        assertEquals(promotion.getStartDate(), LocalDate.of(2024, 1, 1));
        assertEquals(promotion.getEndDate(), LocalDate.of(2024, 12, 31));
    }
    
}
