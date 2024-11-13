package store.model.inventory;

import store.model.discount.Promotion;

import java.util.Map;

/** 프로모션 이름과 프로모션 할인을 매핑한 클래스 */
public class Promotions {
    
    private final Map<String, Promotion> promotions;
    
    public Promotions(Map<String, Promotion> promotions) {
        this.promotions = promotions;
    }
    
    public Promotion getFromName(String name) {
        return promotions.getOrDefault(name, null);
    }

}
