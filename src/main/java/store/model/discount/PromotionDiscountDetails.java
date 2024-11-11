package store.model.discount;

import store.model.inventory.Inventory;
import store.model.order.OrderDetails;
import store.model.orderquantity.OrderProductQuantity;
import store.model.inventory.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class PromotionDiscountDetails implements Iterable<PromotionDiscountDetail> {

    private final List<PromotionDiscountDetail> discountDetails;

    public PromotionDiscountDetails(OrderDetails details, Inventory inventory, LocalDateTime orderTime) {
        List<PromotionDiscountDetail> discountDetails = new ArrayList<>();
        for (Map.Entry<Product, OrderProductQuantity> entry : details.getEntrySet()) {
            Product product = entry.getKey();
            if (product.isPromotionPeriod(orderTime)) {
                addDetails(inventory, entry, discountDetails);
            }
        }
        this.discountDetails = discountDetails;
    }

    private static void addDetails(Inventory inventory, Map.Entry<Product, OrderProductQuantity> entry,
                                   List<PromotionDiscountDetail> discountDetails) {
        Product product = entry.getKey();
        OrderProductQuantity quantity = entry.getValue();
        PromotionDiscountDetail detail = new PromotionDiscountDetail(product, quantity, inventory);
        if (!detail.isEmpty()) {
            discountDetails.add(detail);
        }
    }
    
    public boolean isEmpty() {
        return discountDetails.isEmpty();
    }

    public int getTotalAmount() {
        return discountDetails.stream()
                .mapToInt(PromotionDiscountDetail::getTotalAmountByProduct)
                .sum();
    }
    
    @Override
    public Iterator<PromotionDiscountDetail> iterator() {
        return new PromotionDiscountDetailsIterator();
    }

    private class PromotionDiscountDetailsIterator implements Iterator<PromotionDiscountDetail> {

        private int index;

        @Override
        public boolean hasNext() {
            return index < discountDetails.size();
        }

        @Override
        public PromotionDiscountDetail next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            PromotionDiscountDetail detail = discountDetails.get(index);
            index++;
            return detail;
        }
    }
    
}
