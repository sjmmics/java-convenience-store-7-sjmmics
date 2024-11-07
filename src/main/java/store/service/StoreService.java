package store.service;

import store.model.Inventory;
import store.model.Promotions;
import store.model.ShoppingCart;

import java.time.LocalDateTime;

public interface StoreService {
    void saveInventory(Inventory inventory);
    
    void savePromotions(Promotions promotions);
    
    Inventory getInventory();
    
    void applyPromotionDiscount(ShoppingCart cart, LocalDateTime now);
}
