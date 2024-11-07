package store.service;

import store.model.Inventory;
import store.model.Promotions;
import store.model.ShoppingCart;
import store.repository.StoreRepository;

import java.time.LocalDateTime;

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
    
    @Override
    public void applyPromotionDiscount(ShoppingCart cart, LocalDateTime now) {
        cart.applyPromotionDiscount(getInventory(), now);
        System.out.println(cart);
    }
    
    
    
}
