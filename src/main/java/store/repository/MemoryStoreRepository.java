package store.repository;

import store.model.inventory.Inventory;
import store.model.discount.MembershipDiscountDetail;
import store.model.order.OrderDetails;
import store.model.discount.PromotionDiscountDetails;

/** StoreRepository 인터페이스의 구현체 */
public class MemoryStoreRepository implements StoreRepository {

    private Inventory inventory;

    private OrderDetails orderDetails;

    private PromotionDiscountDetails promotionDiscountDetails;

    private MembershipDiscountDetail membershipDiscountDetail;

    public MemoryStoreRepository() {}

    @Override
    public void saveInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public void saveOrderDetails(OrderDetails details) {
        this.orderDetails = details;
    }

    @Override
    public OrderDetails getOrderDetails() {
        return this.orderDetails;
    }

    @Override
    public PromotionDiscountDetails getPromotionDiscountDetails() {
        return this.promotionDiscountDetails;
    }

    @Override
    public void savePromotionDiscountDetails(PromotionDiscountDetails discountDetails) {
        this.promotionDiscountDetails = discountDetails;
    }


    @Override
    public MembershipDiscountDetail getMembershipDiscountDetail() {
        return membershipDiscountDetail;
    }

    @Override
    public void saveMembershipDiscountDetail(MembershipDiscountDetail detail) {
        this.membershipDiscountDetail = detail;
    }


}
