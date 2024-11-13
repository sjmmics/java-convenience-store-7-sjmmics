package store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.discount.Promotion;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PromotionTest {
    private final static String NAME = "반짝할인";
    
    private final static int CONDITION = 2;
    
    private final LocalDate START_DATE = LocalDate.of(2024, 1, 1);
    
    private final LocalDate END_DATE1 = LocalDate.of(2024, 12, 31);
    
    private final LocalDate END_DATE2 = LocalDate.of(2024, 3, 1);
    
    @Test
    @DisplayName("프로모션 동일성 테스트")
    void promotionEqualsTest() {
        Promotion promotion1 = new Promotion(NAME, START_DATE, END_DATE1, CONDITION);
        Promotion promotion2 = new Promotion(NAME, START_DATE, END_DATE2, CONDITION);
        Promotion promotion3 = new Promotion(NAME, START_DATE, END_DATE1, CONDITION);
        // 모든 인스턴스 필드가 동일해야 동일함
        assertEquals(promotion1, promotion3);
        assertNotEquals(promotion1, promotion2);
    }
}
