package store.model.orderquantity;

import store.model.inventory.Inventory;
import store.model.order.OrderDetails;
import store.model.inventory.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static store.model.orderquantity.OrderQuantityOption.OptionCase.NOT_CONTAIN_BONUS;
import static store.model.orderquantity.OrderQuantityOption.OptionCase.OUT_OF_PROMOTION_STOCK;
import static store.util.Constants.BONUS_COUNT;

/** 재고 및 프로모션 할인에 따른 주문 변경 가능 선택지(OrderQuantityOptions) 객체를 생성하는 팩토리 클래스 */
public class QuantityOptionsFactory {

    private final OrderDetails details;

    public QuantityOptionsFactory(OrderDetails details) {
        this.details = details;
    }

    public OrderQuantityOptions getOptions(Inventory inventory, LocalDateTime orderTime) {
        List<OrderQuantityOption> options = new ArrayList<>();
        for (Map.Entry<Product, OrderProductQuantity> entry : details.getEntrySet()) {
            OrderQuantityOption option = getOptionElseNull(inventory, orderTime, entry);
            if (option != null) {
                options.add(option);
            }
        }
        return new OrderQuantityOptions(options);
    }

    private OrderQuantityOption getOptionElseNull(Inventory inventory, LocalDateTime orderTime,
                                                  Map.Entry<Product, OrderProductQuantity> entry) {
        Product product = entry.getKey();
        OrderProductQuantity quantity = entry.getValue();
        if (!isDiscountPeriod(orderTime, product)) {
            return null;
        }
        return checkIncludeAllBonus(product, quantity, inventory);
    }

    private boolean isDiscountPeriod(LocalDateTime orderTime, Product product) {
        return product.isPromotionPeriod(orderTime);
    }

    private OrderQuantityOption checkIncludeAllBonus(Product product, OrderProductQuantity quantity,
                                                     Inventory inventory) {
        int promotionStock = inventory.getPromotionStock(product);
        if (doesExceedPromotionStock(quantity.getTotal(), promotionStock)) {
            int promotionStockShortage = quantity.getTotal() - promotionStock;
            return new OrderQuantityOption(product, OUT_OF_PROMOTION_STOCK, promotionStockShortage);
        }
        int promotionCondition = product.getPromotionCondition();
        if (notIncludeBonusAndCanGetBonus(quantity.getTotal(), promotionStock, promotionCondition)) {
            return new OrderQuantityOption(product, NOT_CONTAIN_BONUS, BONUS_COUNT);
        }
        return null;
    }

    private boolean doesExceedPromotionStock(int quantity, int promotionStock) {
        return quantity > promotionStock;
    }

    private boolean notIncludeBonusAndCanGetBonus(int totalQuantity, int promotionStock,
                                                  int promotionCondition) {
        if (includeAllPromotionBonus(totalQuantity, promotionCondition)) {
            return false;
        }
        return totalQuantity + BONUS_COUNT <= promotionStock;
    }

    private boolean includeAllPromotionBonus(int quantity, int promotionCondition) {
        return (quantity + BONUS_COUNT) % (promotionCondition + BONUS_COUNT) != 0;
    }

}
