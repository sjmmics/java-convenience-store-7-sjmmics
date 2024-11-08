package store.controller;

import camp.nextstep.edu.missionutils.DateTimes;
import store.exception.OutOfPromotionStockException;
import store.model.Inventory;
import store.model.Promotions;
import store.model.ShoppingCart;
import store.model.ShoppingCartFactory;
import store.service.StoreService;
import store.util.InventoryInitializer;
import store.util.PromotionsInitializer;
import store.view.InputView;
import store.view.OutputView;

import java.time.LocalDateTime;

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
        ShoppingCartFactory factory = new ShoppingCartFactory();
        ShoppingCart cart = getCart(factory);
        applyPromotionDiscount(cart, DateTimes.now());

    }
    
    private void applyPromotionDiscount(ShoppingCart cart, LocalDateTime now) {
        while (true) {
            try {
                service.applyPromotionDiscount(cart, DateTimes.now());
            } catch (OutOfPromotionStockException e) {
                if (inputView.buyRegularStock()) {
                
                }
            }
        }
    }
    
    private ShoppingCart getCart(ShoppingCartFactory factory) {
        while (true) {
            try {
                return factory.get(inputView.getOrder(), service.getInventory());
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
