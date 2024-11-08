package store.model;

import store.exception.OutOfPromotionStockException;

import java.time.LocalDateTime;
import java.util.Map;

public class ShoppingCart {

    private final Map<Product, Quantity> orderProducts;
    
    public ShoppingCart(Map<Product, Quantity> orderProducts) {
        this.orderProducts = orderProducts;
    }
    
    public void applyPromotionDiscount(Inventory inventory, LocalDateTime orderTime) {
        for (Map.Entry<Product, Quantity> entry : orderProducts.entrySet()) {
            Product product = entry.getKey();
            Quantity quantity = entry.getValue();
            // 기간 확인(orderTime, Product -> promotion -> startDate && endDate)
            boolean discountPeriod = isDiscountPeriod(orderTime, product);
            if (discountPeriod) {
                calculateDiscount(product, quantity, inventory);
            }
            if (!discountPeriod) {
                calculateNonDiscount(product, quantity, inventory);
            }
        }
    }
    
    private boolean isDiscountPeriod(LocalDateTime orderTime, Product product) {
    
    }
    
    private void calculateDiscount(Product product, Quantity quantity, Inventory inventory) {
        // 프로모션 개수로 떨어지는지 확인(Quantity, condition <- promotion <- product)
        int promotionCondition = product.getPromotionCondition();
        int totalQuantity = quantity.getTotal();
        int deficiency = totalQuantity % promotionCondition;
        boolean allIncludePromotionCount = isAllIncludePromotionCount(totalQuantity, promotionCondition);
        if (allIncludePromotionCount && isNotExceedPromotionStock(totalQuantity, inventory.getStock(product))) {
            // 프로모션 차감
        }
        if (allIncludePromotionCount && !isNotExceedPromotionStock(totalQuantity, inventory.getStock(product))) {
            // 일반 재고로 구매할지 확인
            throw new OutOfPromotionStockException();
        }
        if (!allIncludePromotionCount
                && isNotExceedPromotionStock(totalQuantity + deficiency, inventory.getStock(product))) {
        
        }
        // 증정품 추가한 개수가 프로모션 재고보다 같거나 큰지 확인(Quantity, condition, Stock <- inventory)
        
    }
    
    private boolean isNotExceedPromotionStock(int quantity, Stock stock) {
    }
    
    private boolean isAllIncludePromotionCount(int quantity, int promotionCondition) {
    
    }
    
    private void calculateNonDiscount(Product product, Quantity quantity, Inventory inventory) {
    
    }
    
    
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Product, Quantity> entry : orderProducts.entrySet()) {
            sb.append(entry.getKey()).append(entry.getValue());
        }
        return sb.toString();
    }


}
