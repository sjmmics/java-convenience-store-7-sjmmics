package store.repository;

import store.model.Inventory;
import store.model.Promotions;

public interface StoreRepository {
    void saveInventory(Inventory inventory);
    
    void savePromotions(Promotions promotions);
    
    Inventory getInventory();
    
}
