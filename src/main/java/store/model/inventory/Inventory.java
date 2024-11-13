package store.model.inventory;

import store.model.order.OrderDetails;
import store.model.orderquantity.OrderProductQuantity;

import java.util.Map;

/** 편의점 상품 재고를 담당하는 클래스 */
public class Inventory {
    
    private final Map<Product, Stock> inventory;
    
    public Inventory(Map<Product, Stock> inventory) {
        this.inventory = inventory;
    }
    
    public boolean doNotContainsProduct(String name) {
        for (Map.Entry<Product, Stock> entry : inventory.entrySet()) {
            String productName = entry.getKey().getName();
            if (productName.equals(name)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean exceedStockOfProductNameAndQuantity(String orderProductName, int orderQuantity) {
        for (Map.Entry<Product, Stock> entry : inventory.entrySet()) {
            Product product = entry.getKey();
            Stock stock = entry.getValue();
            if (product.equalsName(orderProductName) && orderQuantity > stock.getTotal()) {
                return true;
            }
        }
        return false;
    }

    public Product getProduct(String name) {
        for (Map.Entry<Product, Stock> entry : inventory.entrySet()) {
            Product product = entry.getKey();
            String productName = product.getName();
            if (productName.equals(name)) {
                return product;
            }
        }
        return new Product("", 0, null);
    }

    public int getPromotionStock(Product product) {
        Stock stock = inventory.get(product);
        return stock.getPromotion();
    }

    public int getPromotionStockShortage(Product product, int totalQuantity) {
        int promotionStock = this.getPromotionStock(product);
        int promotionStockShortage = totalQuantity - promotionStock;
        return Math.max(0, promotionStockShortage);
    }

    public void setInventoryDeductionByOrder(OrderDetails details) {
        for (Map.Entry<Product, OrderProductQuantity> entry : details.getEntrySet()) {
            Product product = entry.getKey();
            OrderProductQuantity saleQuantity = entry.getValue();
            Stock stock = inventory.get(product);
            stock.setBySale(saleQuantity);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Product, Stock> entry : inventory.entrySet()) {
            Product product = entry.getKey();
            Stock stock = entry.getValue();
            addStringInventoryDetails(stringBuilder, product, stock);
        }
        return stringBuilder.toString();
    }

    private void addStringInventoryDetails(StringBuilder stringBuilder,
                                           Product product, Stock stock) {
        if (product.containsPromotion()) {
            addStringOfProductAndBothStock(stringBuilder, product, stock);
        }
        if (!product.containsPromotion()) {
            addStringOfProductAndStock(stringBuilder, product, stock);
        }
    }

    private void addStringOfProductAndBothStock(StringBuilder stringBuilder,
                                                Product product, Stock stock) {
        stringBuilder.append(getProductAndUnitPrice(product.getName(), product.getUnitPrice()))
                .append(stock.getPromotionString())
                .append(" ")
                .append(product.getPromotionName())
                .append("\n")
                .append(getProductAndUnitPrice(product.getName(), product.getUnitPrice()))
                .append(stock.getRegularString())
                .append("\n");
    }

    private void addStringOfProductAndStock(StringBuilder stringBuilder,
                                            Product product, Stock stock) {
        stringBuilder.append(getProductAndUnitPrice(product.getName(), product.getUnitPrice()))
                .append(stock.getRegularString())
                .append("\n");
    }

    private String getProductAndUnitPrice(String productName, int unitPrice) {
        return String.format("- %s %,d원 ", productName, unitPrice);
    }
    
}
