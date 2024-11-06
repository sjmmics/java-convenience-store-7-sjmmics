package store.controller;

import store.model.Inventory;
import store.model.OrderProducts;
import store.model.Promotions;
import store.service.StoreService;
import store.util.InventoryInitializer;
import store.util.PromotionsInitializer;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    
    private final InputView inputView;
    
    private final OutputView outputView;
    
    private final StoreService service;
    
    public StoreController(InputView inputView, OutputView outputView, StoreService service) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.service = service;
    }
    
    public void run() {
        initializePromotionsAndInventoryAndSave();
        outputView.printInventory(service.getInventory());
        OrderProducts orderProducts = getOrderItems(service.getInventory());
        
    }
    
    private OrderProducts getOrderItems(Inventory inventory) {
        while (true) {
            try {
                return new OrderProducts(inputView.getOrder(), inventory);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    private void initializePromotionsAndInventoryAndSave() {
        Promotions promotions = getInitializedPromotions();
        service.savePromotions(promotions);
        Inventory inventory = getInitializedInventory(promotions);
        service.saveInventory(inventory);
    }
    
    private Promotions getInitializedPromotions() {
        PromotionsInitializer promotionsInitializer = new PromotionsInitializer();
        return promotionsInitializer.get();
    }
    
    private Inventory getInitializedInventory(Promotions promotions) {
        InventoryInitializer inventoryInitializer = new InventoryInitializer();
        return inventoryInitializer.get(promotions);
    }
}
