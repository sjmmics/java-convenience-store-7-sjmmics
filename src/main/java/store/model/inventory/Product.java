package store.model.inventory;

import store.model.discount.Promotion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/** 편의점에서 판매하는 상품을 담당하는 클래스 */
public class Product {

    private final String name;
    
    private final int price;
    
    private final Promotion promotion;

    public Product(String name, int price, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
    }

    public int getPromotionCondition() {
        return promotion.getPromotionCondition();
    }

    public boolean isPromotionPeriod(LocalDateTime orderTime) {
        if (this.promotion == null) {
            return false;
        }
        LocalDate startDate = promotion.getStartDate();
        LocalDate endDate = promotion.getEndDate();
        LocalDate endDateInclusive = endDate.plusDays(1);
        LocalDate orderDate = orderTime.toLocalDate();
        return (!orderDate.isBefore(startDate)) && orderDate.isBefore(endDateInclusive);
    }

    public String getName() {
        return name;
    }
    
    public int getUnitPrice() {
        return price;
    }
    
    public String getPromotionName() {
        if (promotion == null) {
            return "";
        }
        return promotion.toString();
    }

    public boolean containsPromotion() {
        return promotion != null;
    }
    
    public boolean equalsName(String orderProductName) {
        return this.name.equals(orderProductName);
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

}