package store.model.discount;

import store.model.inventory.Inventory;
import store.model.orderquantity.OrderProductQuantity;
import store.model.inventory.Product;

import static store.util.Constants.BONUS_COUNT;

public class PromotionDiscountDetail {

    private final String productName;

    private final int bonusCount;

    private final int totalAmount;

    public PromotionDiscountDetail(Product product, OrderProductQuantity quantity, Inventory inventory) {
        int promotionCondition = product.getPromotionCondition();
        int promotionStock = inventory.getPromotionStock(product);
        int totalQuantity = quantity.getTotal();
        int unitPrice = product.getUnitPrice();
        this.productName = product.getName();
        this.bonusCount = getPromotionBonus(totalQuantity, promotionCondition, promotionStock);
        this.totalAmount = bonusCount * unitPrice;
    }

    private int getPromotionBonus(int totalQuantity, int condition, int promotionStock) {
        int promotionStockDeduction = getPromotionStockDeduction(totalQuantity, promotionStock);
        return promotionStockDeduction / (condition + BONUS_COUNT);
    }

    private int getPromotionStockDeduction(int totalQuantity, int promotionStock) {
        return Math.min(totalQuantity, promotionStock);
    }

    public String getProductName() {
        return this.productName;
    }

    public int getBonusCount() {
        return this.bonusCount;
    }

    public int getTotalAmountByProduct() {
        return this.totalAmount;
    }

    public boolean isEmpty() {
        return this.bonusCount == 0;
    }
    
}
