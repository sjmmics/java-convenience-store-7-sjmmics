package store.model;

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
            if (product.canPromotionDiscount(orderTime)) {
                quantity.setPromotionSale(inventory.getStock(product),
                        product.getPromotionCondition());
            }
        }
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
