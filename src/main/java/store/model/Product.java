package store.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Product {
    
    private final String name;
    
    private final int price;
    
    private final Promotion promotion;

    public Product(String name, int price, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
    }
    
    public String getName() {
        return name;
    }
    
    public int getPrice() {
        return price;
    }
    
    public String getPromotionName() {
        if (promotion == null) {
            return "";
        }
        return promotion.toString();
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Product product = (Product) object;
        return price == product.price && Objects.equals(name, product.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
    
    public boolean notContainsPromotion() {
        return promotion == null;
    }
    
    public boolean containsPromotion() {
        return promotion != null;
    }
    
    public boolean equalsName(String orderProductName) {
        return this.name.equals(orderProductName);
    }
    
    public boolean canPromotionDiscount(LocalDateTime orderTime) {
        return true;
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", promotion=" + promotion +
                '}';
    }
    
    public int getPromotionCondition() {
        return promotion.getPromotionCondition();
    }
}