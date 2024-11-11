package store.service;

import store.model.inventory.Inventory;
import store.model.order.OrderDetails;
import store.model.orderquantity.OrderQuantityOptions;
import store.model.order.Receipt;

import java.time.LocalDateTime;

/**
 * Controller에서 전달 받은 모델을 가지고 주요 비즈니스 로직을 실행하여 결과값을 Repository에 저장
 * Controller의 요청에 있으면 Repository에서 불러와서 반환하는 인터페이스
 */
public interface StoreService {

    Inventory getInventory();

    void initPromotionsAndInventory();

    void discountMembership(boolean want);

    Receipt getReceipt();

    OrderQuantityOptions getOrderQuantityOptions(LocalDateTime orderTime);

    void discountPromotion(LocalDateTime orderTime);

    void adjustQuantity(OrderQuantityOptions options, LocalDateTime orderTime);

    void saveOrderDetails(OrderDetails orderDetails);

    void deductInventoryBySale();
    
}
