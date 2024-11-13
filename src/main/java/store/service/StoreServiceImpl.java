package store.service;

import store.model.discount.MembershipDiscountDetail;
import store.model.orderquantity.OrderQuantityAdjuster;
import store.model.inventory.Inventory;
import store.model.order.OrderDetails;
import store.model.orderquantity.OrderQuantityOptions;
import store.model.discount.PromotionDiscountDetails;
import store.model.inventory.Promotions;
import store.model.orderquantity.QuantityOptionsFactory;
import store.model.order.Receipt;
import store.repository.StoreRepository;
import store.util.InventoryInitializer;
import store.util.PromotionsInitializer;

import java.time.LocalDateTime;

import static store.util.InventoryInitializer.INVENTORY_FILE_PATH;
import static store.util.PromotionsInitializer.PROMOTION_FILE_PATH;

/** StoreService 인터페이스의 구현체 */
public class StoreServiceImpl implements StoreService {

    private final StoreRepository repository;

    public StoreServiceImpl(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Inventory getInventory() {
        return repository.getInventory();
    }

    @Override
    public OrderQuantityOptions getOrderQuantityOptions(LocalDateTime orderTime) {
        OrderDetails orderDetails = repository.getOrderDetails();
        QuantityOptionsFactory factory = new QuantityOptionsFactory(orderDetails);
        Inventory inventory = repository.getInventory();
        return factory.getOptions(inventory, orderTime);
    }

    @Override
    public void discountPromotion(LocalDateTime orderTime) {
        OrderDetails orderDetails = repository.getOrderDetails();
        Inventory inventory = repository.getInventory();
        PromotionDiscountDetails discountDetails =
                new PromotionDiscountDetails(orderDetails, inventory, orderTime);
        repository.savePromotionDiscountDetails(discountDetails);

    }

    @Override
    public void adjustQuantity(OrderQuantityOptions options, LocalDateTime orderTime) {
        OrderDetails orderDetails = repository.getOrderDetails();
        OrderQuantityAdjuster adjuster = new OrderQuantityAdjuster(orderDetails);
        adjuster.applyOfOrderDetailsAndQuantityOptions(options);
        Inventory inventory = repository.getInventory();
        adjuster.applyOfOrderDetailsAndInventory(inventory, orderTime);
        repository.saveOrderDetails(orderDetails);
    }

    @Override
    public void saveOrderDetails(OrderDetails orderDetails) {
        repository.saveOrderDetails(orderDetails);
    }

    @Override
    public void deductInventoryBySale() {
        OrderDetails details = repository.getOrderDetails();
        Inventory inventory = repository.getInventory();
        inventory.setInventoryDeductionByOrder(details);
        repository.saveInventory(inventory);
    }

    @Override
    public void initPromotionsAndInventory() {
        PromotionsInitializer promotionsInitializer = new PromotionsInitializer();
        Promotions promotions = promotionsInitializer.get(PROMOTION_FILE_PATH);
        InventoryInitializer inventoryInitializer = new InventoryInitializer();
        Inventory inventory = inventoryInitializer.get(promotions, INVENTORY_FILE_PATH);
        // repository.savePromotions(promotions);
        repository.saveInventory(inventory);
    }

    @Override
    public void discountMembership(boolean want) {
        OrderDetails orderDetails = repository.getOrderDetails();
        if (want) {
            MembershipDiscountDetail detail = new MembershipDiscountDetail(orderDetails);
            repository.saveMembershipDiscountDetail(detail);
        }
        if (!want) {
            MembershipDiscountDetail detail = new MembershipDiscountDetail();
            repository.saveMembershipDiscountDetail(detail);
        }
    }

    @Override
    public Receipt getReceipt() {
        OrderDetails orderDetails = repository.getOrderDetails();
        PromotionDiscountDetails promotionDiscountDetails =
                repository.getPromotionDiscountDetails();
        MembershipDiscountDetail membershipDiscountDetail =
                repository.getMembershipDiscountDetail();
        return new Receipt(orderDetails, promotionDiscountDetails, membershipDiscountDetail);
    }
    
}
