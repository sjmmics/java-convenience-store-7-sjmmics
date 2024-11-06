package store.util;

import store.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class InventoryInitializer {
    
    private static final int NAME = 0;
    
    private static final int PRICE = 1;
    
    private static final int QUANTITY = 2;
    
    private static final int PROMOTION_NAME = 3;
    
    public Inventory get(Promotions promotions) {
        Path path = Path.of("src/main/resources/products.md");
        Map<Product, Stock> inventory = new LinkedHashMap<>();
        try {
            List<String> lines = Files.readAllLines(path, UTF_8);
            for (int i = 1; i < lines.size(); i++) {
                String[] productInfo = lines.get(i).split(",");
                String productName = productInfo[NAME];
                int productPrice = Integer.parseInt(productInfo[PRICE]);
                int productQuantity = Integer.parseInt(productInfo[QUANTITY]);
                String promotionName = productInfo[PROMOTION_NAME];
                Promotion promotion = promotions.getFromName(promotionName);
                Product product = new Product(productName, productPrice, promotion);
                
                if (!inventory.containsKey(product)) {
                    Stock stock = new Stock();
                    inventory.put(product, stock);
                }
                
                Stock stock = inventory.get(product);
                stock.addOfQuantityAndPromotion(productQuantity, promotion);
                inventory.put(product, stock);
                
            }
        } catch (IOException e) {
            System.out.println();
        }
        return new Inventory(inventory);
    }
    
}
