package store.model;

import java.time.LocalDate;
import java.util.Objects;

public class Promotion {
    
    private final String name;
    
    private final LocalDate startDate;
    
    private final LocalDate endDate;
    
    private final int condition;
    
    public Promotion(String name, LocalDate startDate,
                     LocalDate endDate, int condition) {
        
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.condition = condition;
    }
    
    public String toString() {
        return this.name;
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Promotion promotion = (Promotion) object;
        return condition == promotion.condition && Objects.equals(name, promotion.name)
                && Objects.equals(startDate, promotion.startDate)
                && Objects.equals(endDate, promotion.endDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, endDate, condition);
    }
    
    
    public int getPromotionCondition() {
        return this.condition;
    }
}
