package store.view;

import store.model.Inventory;
import store.model.Product;
import store.model.Stock;
import store.util.Constants;

import java.util.Map;

public class ConsoleOutputView implements OutputView {
    
    private static final String MESSAGE_INTRODUCTION =
            "안녕하세요. w편의점입니다.\n현재 보유하고 있는 상품입니다.\n";
    
    @Override
    public void printInventory(Inventory inventory) {
        System.out.println(MESSAGE_INTRODUCTION);
        for (Map.Entry<Product, Stock> entry : inventory.getEntrySet()) {
            Product product = entry.getKey();
            Stock stock = entry.getValue();
            if (product.ContainsPromotion()) {
                printProductAndTotalStock(product, stock);
            }
            if (product.notContainsPromotion()) {
                printProductAndRegularStock(product, stock);
            }
        }
        System.out.println();
    }
    
    private void printProductAndTotalStock(Product product, Stock stock) {
        printProduct(product);
        System.out.print(stock.getPromotionString());
        System.out.print(" ");
        System.out.println(product.getPromotionName());
        printProduct(product);
        System.out.println(stock.getRegularString());
    }
    
    private void printProduct(Product product) {
        System.out.print("- ");
        System.out.print(product.getName());
        System.out.print(" ");
        System.out.print(Constants.THOUSAND_COMMA.format(product.getPrice()));
        System.out.print("원 ");
    }
    
    private void printProductAndRegularStock(Product product, Stock stock) {
        printProduct(product);
        System.out.println(stock.getRegularString());
    }
}
