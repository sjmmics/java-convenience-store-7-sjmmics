package store.repository;

import store.model.Inventory;
import store.model.Promotions;

public class MemoryRepository implements StoreRepository {
    private Inventory inventory;
    
    private Promotions promotions;
    
    @Override
    public void saveInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    
    @Override
    public void savePromotions(Promotions promotions) {
        this.promotions = promotions;
    }
    
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
