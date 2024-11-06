package store.model;

import java.util.Map;

public class ShoppingCart {

    private final Map<String, Integer> orderProducts;

    public ShoppingCart(Map<Product, Quantity> orderProducts) {
        this.orderProducts = orderProducts;
    }


}
