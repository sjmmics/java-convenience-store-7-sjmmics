package store.service;

import store.model.Inventory;
import store.model.Promotions;
import store.repository.StoreRepository;

public class StoreServiceImpl implements StoreService {
    
    private final StoreRepository repository;
    
    public StoreServiceImpl(StoreRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void saveInventory(Inventory inventory) {
        repository.saveInventory(inventory);
    }
    
    @Override
    public void savePromotions(Promotions promotions) {
        repository.savePromotions(promotions);
    }
    
    @Override
    public Inventory getInventory() {
        return repository.getInventory();
    }
}
