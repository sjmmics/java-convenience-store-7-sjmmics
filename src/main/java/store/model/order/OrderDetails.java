package store.model.order;

import store.model.inventory.Product;
import store.model.orderquantity.OrderProductQuantity;

import java.util.Map;
import java.util.Set;

/** 주문 상세 정보를 담당하는 클래스 */
public class OrderDetails {

    private final Map<Product, OrderProductQuantity> details;

    public OrderDetails(Map<Product, OrderProductQuantity> details) {
        this.details = details;
    }

    public Set<Map.Entry<Product, OrderProductQuantity>> getEntrySet() {
        return details.entrySet();
    }

    public void adjustQuantityNotContainsBonus(Product product, int bonusCount) {
        OrderProductQuantity quantity = details.get(product);
        quantity.plusTotal(bonusCount);
    }

    public void adjustQuantityDoesNotBuyRegular(Product product, int shortage) {
        OrderProductQuantity quantity = details.get(product);
        quantity.minusTotal(shortage);
    }

    public int getTotalQuantity() {
        return details.values()
                .stream()
                .mapToInt(OrderProductQuantity::getTotal)
                .sum();
    }

    public int getTotalAmount() {
        return details.entrySet()
                .stream()
                .mapToInt(OrderDetails::getAmountByProduct)
                .sum();
    }

    private static int getAmountByProduct(Map.Entry<Product, OrderProductQuantity> entry) {
        Product product = entry.getKey();
        OrderProductQuantity quantity = entry.getValue();
        int unitPrice = product.getUnitPrice();
        int totalUnitQuantity = quantity.getTotal();
        return unitPrice * totalUnitQuantity;
    }

}
