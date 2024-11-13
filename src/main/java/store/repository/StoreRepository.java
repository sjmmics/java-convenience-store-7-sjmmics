package store.repository;

import store.model.inventory.Inventory;
import store.model.discount.MembershipDiscountDetail;
import store.model.order.OrderDetails;
import store.model.discount.PromotionDiscountDetails;

/** 주요 모델를 저장하며, service의 요청이 있으면 저장한 객체를 반환한다. */
public interface StoreRepository {

    void saveInventory(Inventory inventory);
    
    Inventory getInventory();

    void saveOrderDetails(OrderDetails details);

    OrderDetails getOrderDetails();

    PromotionDiscountDetails getPromotionDiscountDetails();

    void savePromotionDiscountDetails(PromotionDiscountDetails discountDetails);

    MembershipDiscountDetail getMembershipDiscountDetail();

    void saveMembershipDiscountDetail(MembershipDiscountDetail detail);
    
}
