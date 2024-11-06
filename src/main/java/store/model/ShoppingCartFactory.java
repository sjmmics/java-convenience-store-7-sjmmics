package store.model;

import store.util.Constants;
import store.util.StringValidator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShoppingCartFactory {

    private static final int NAME = 0;

    private static final int QUANTITY = 1;

    private final static String REGEX = "^[가-힣]+-[0-9]+(,([가-힣]+-[0-9]+))*$";

    private final static Pattern PATTERN = Pattern.compile(REGEX);

    public ShoppingCart get(String orderLine, Inventory inventory) {
        validateFormat(orderLine);
        Map<String, String> productNamesAndQuantityString = getOrders(orderLine);
        validateNumberRange(productNamesAndQuantityString);
        Map<String, Integer> productNamesAndQuantity =
                getOrderItems(productNamesAndQuantityString);
        validateContent(productNamesAndQuantity, inventory);
        Map<Product, Quantity> productAndQuantity =
                getOrderProducts(productNamesAndQuantity, inventory);
        return new ShoppingCart(productAndQuantity);
    }

    private void validateFormat(String order) {
        if (StringValidator.isEmpty(order)) {
            throw new IllegalArgumentException(Constants.EMPTY_INPUT_EXCEPTION_MESSAGE);
        }
        if (StringValidator.containsBlank(order)) {
            throw new IllegalArgumentException(Constants.EXCEPTION_MESSAGE_WRONG_FORMAT);
        }
        if (isIncorrectFormat(order)) {
            throw new IllegalArgumentException(Constants.EXCEPTION_MESSAGE_WRONG_FORMAT);
        }
    }

    private boolean isIncorrectFormat(String order) {
        Matcher matcher = PATTERN.matcher(order);
        return !matcher.matches();
    }

    private Map<String, String> getOrders(String orderLine) {
        Map<String, String> orders = new HashMap<>();
        String[] orderLines = orderLine.split(",");
        for (String rawOrderLine : orderLines) {
            String[] orderLineSplit = rawOrderLine.split(",");
            for (String rawOrderItemAndQuantity : orderLineSplit) {
                putOrder(orders, rawOrderItemAndQuantity);
            }
        }
        return orders;
    }

    private void putOrder(Map<String, String> orders, String rawOrderItemAndQuantity) {
        String orderItemAndQuantity =
                rawOrderItemAndQuantity.substring(1, rawOrderItemAndQuantity.length() - 1);
        String[] orderItemAndQuantitySplit = orderItemAndQuantity.split("-");
        String name = orderItemAndQuantitySplit[NAME];
        String quantity = orderItemAndQuantitySplit[QUANTITY];
        orders.put(name, quantity);
    }


    private void validateNumberRange(Map<String, String> orders) {
        for (Map.Entry<String, String> entry : orders.entrySet()) {
            try {
                Integer.parseInt(entry.getKey());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
        }
    }

    private Map<String, Integer> getOrderItems(Map<String, String> orders) {
        Map<String, Integer> orderItems = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : orders.entrySet()) {
            String name = entry.getKey();
            int quantity = Integer.parseInt(entry.getValue());
            orderItems.put(name, quantity);
        }
        return orderItems;
    }

    private void validateContent(Map<String, Integer> orders, Inventory inventory) {
        if (notContainsItem(orders, inventory)) {
            throw new IllegalArgumentException();
        }
        if (exceedStock(orders, inventory)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean exceedStock(Map<String, Integer> orders, Inventory inventory) {
        for (Map.Entry<String, Integer> entry : orders.entrySet()) {
            String orderProductName = entry.getKey();
            int orderQuantity = entry.getValue();
            if (inventory.exceedStockOfProductNameAndQuantity(orderProductName, orderQuantity)) {
                return true;
            }
        }
        return false;
    }

    private boolean notContainsItem(Map<String, Integer> orders, Inventory inventory) {
        for (Map.Entry<String, Integer> entry : orders.entrySet()) {
            String name = entry.getKey();
            if (inventory.notContainItem(name)) {
                return true;
            }
        }
        return false;
    }

    private Map<Product, Quantity> getOrderProducts(Map<String, Integer> namesAndQuantity,
                                                    Inventory inventory) {
        Map<Product, Quantity> cart = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : namesAndQuantity.entrySet()) {
            String name = entry.getKey();
            int totalQuantity = entry.getValue();
            Product product = inventory.getProduct(name);
            int promotionQuantity = 0;
            if (product.ContainsPromotion()) {

            }
        }

    }
}
