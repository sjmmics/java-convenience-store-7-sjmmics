package store.util;

import store.model.inventory.Inventory;
import store.model.inventory.Product;
import store.model.inventory.Stock;
import store.model.discount.Promotion;
import store.model.inventory.Promotions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 파일로부터 편의점 재고 현황 정보를 불러와서 재고 객체(Inventory)를 초기화하는 클래스
 * 파일이 잘못되었으면 IOException를 호출한다.
 */
public class InventoryInitializer {
    
    public static final String INVENTORY_FILE_PATH = "src/main/resources/products.md";
    
    private static final int NAME_INDEX = 0;
    
    private static final int PRICE_INDEX = 1;
    
    private static final int QUANTITY_INDEX = 2;
    
    private static final int PROMOTION_NAME_INDEX = 3;

    private static final String PROMOTION_NULL = "null";

    public Inventory get(Promotions promotions, String pathLine) {
        Path path = Path.of(pathLine);
        Map<Product, Stock> inventory = new LinkedHashMap<>();
        try {
            List<String> lines = Files.readAllLines(path, UTF_8);
            initInventory(inventory, lines, promotions);
        } catch (IOException e) {
            System.out.println(ExceptionMessage.WRONG_FILE);
        }
        return new Inventory(inventory);
    }

    private void initInventory(Map<Product, Stock> inventory,
                               List<String> inventoryInfos, Promotions promotions) {
        for (int i = 1; i < inventoryInfos.size(); i++) {
            String inventoryInfoLine = inventoryInfos.get(i);
            String[] inventoryInfo = inventoryInfoLine.split(",");
            Product product = getProduct(inventoryInfo, promotions);
            Stock stock = getStock(inventory, inventoryInfo, promotions);
            inventory.put(product, stock);
        }
    }

    private Product getProduct(String[] inventoryInfo, Promotions promotions) {
        String productName = inventoryInfo[NAME_INDEX];
        int price = Integer.parseInt(inventoryInfo[PRICE_INDEX]);
        String promotionName = inventoryInfo[PROMOTION_NAME_INDEX];
        Promotion promotion = promotions.getFromName(promotionName);
        return new Product(productName, price, promotion);
    }

    private Stock getStock(Map<Product, Stock> inventory,
                           String[] inventoryInfo, Promotions promotions) {
        Product product = getProduct(inventoryInfo, promotions);
        String promotionName = inventoryInfo[PROMOTION_NAME_INDEX];
        Stock stock = inventory.getOrDefault(product, new Stock());
        int quantity = Integer.parseInt(inventoryInfo[QUANTITY_INDEX]);
        setStockByPromotionName(stock, quantity, promotionName);
        return stock;
    }

    private void setStockByPromotionName(Stock stock, int quantity, String promotionName) {
        if (promotionName.equals(PROMOTION_NULL)) {
            stock.setRegular(quantity);
            return;
        }
        stock.setPromotion(quantity);
    }

}
