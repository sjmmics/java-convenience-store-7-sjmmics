package store.model;

import java.util.Map;
import java.util.Set;

public class Inventory {
    
    private final Map<Product, Stock> inventory;
    
    public Inventory(Map<Product, Stock> inventory) {
        this.inventory = inventory;
    }
    
    public Set<Map.Entry<Product, Stock>> getEntrySet() {
        return inventory.entrySet();
    }
    
    public boolean notContainItem(String name) {
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
    
    public boolean isEnoughPromotionSale(Product product, int totalPurchaseQuantity) {
        return true;
    }
    
    public boolean isNotEnoughPromotionSale(Product product, int purchaseQuantity) {
        return true;
    }
    
    public Stock getStock(Product product) {
        return inventory.get(product);
    }
}
