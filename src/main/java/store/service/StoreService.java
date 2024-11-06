package store.service;

import store.model.Inventory;
import store.model.Promotions;

public interface StoreService {
    void saveInventory(Inventory inventory);
    
    void savePromotions(Promotions promotions);
    
    Inventory getInventory();
    
}
