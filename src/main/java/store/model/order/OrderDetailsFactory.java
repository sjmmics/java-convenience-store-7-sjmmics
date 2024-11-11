package store.model.order;

import store.model.inventory.Inventory;
import store.model.inventory.Product;
import store.model.orderquantity.OrderProductQuantity;
import store.util.ExceptionMessage;
import store.util.StringValidator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 주문 정보 객체(OrderDetails)를 만드는 팩토리 클래스
 * 주문 정보 문자열을 받아서 검증하며 형식이 맞지 않으면 IllegalException를 호출한다.
 */
public class OrderDetailsFactory {

    private static final int NAME_INDEX = 0;

    private static final int QUANTITY_INDEX = 1;

    private final static String CORRECT_FORMAT_REGEX = "^\\[[가-힣]+-[0-9]+](,\\[[가-힣]+-[0-9]+])*$";

    private final static Pattern PATTERN = Pattern.compile(CORRECT_FORMAT_REGEX);

    private final static String REMOVE_ALL_DELIMITERS = "(],\\[)|-";
    
    public OrderDetails create(String orderLine, Inventory inventory) throws IllegalArgumentException {
        validateFormat(orderLine);
        validateDuplicateName(orderLine);
        Map<String, String> namesAndQuantityLines = getOrders(orderLine);
        validateNumberRange(namesAndQuantityLines);
        Map<String, Integer> namesAndQuantity = getOrderItems(namesAndQuantityLines);
        validateContent(namesAndQuantity, inventory);
        Map<Product, OrderProductQuantity> productAndQuantity = getOrderProducts(namesAndQuantity, inventory);
        return new OrderDetails(productAndQuantity);
    }

    private void validateDuplicateName(String orderLine) throws IllegalArgumentException {
        List<String> mayIncludeDuplicateNames = getNamesIncludeDuplicate(orderLine);
        Set<String> removedDuplicateNames = new HashSet<>(mayIncludeDuplicateNames);
        if (mayIncludeDuplicateNames.size() != removedDuplicateNames.size()) {
            throw new IllegalArgumentException(ExceptionMessage.DUPLICATE_NAMES.toString());
        }
    }
    
    private List<String> getNamesIncludeDuplicate(String orderLine) {
        List<String> mayIncludeDuplicateNames = new ArrayList<>();
        String trimmedOrderLine = orderLine.substring(1, orderLine.length() - 1);
        String[] orders = trimmedOrderLine.split(REMOVE_ALL_DELIMITERS);
        for (int i = 0 ; i < orders.length; i++) {
            if (i % 2 == 0) {
                mayIncludeDuplicateNames.add(orders[i]);
            }
        }
        return mayIncludeDuplicateNames;
    }
    
    private void validateFormat(String order) throws IllegalArgumentException {
        if (StringValidator.IS_EMPTY(order)) {
            throw new IllegalArgumentException(ExceptionMessage.EMPTY_INPUT.toString());
        }
        if (StringValidator.CONTAINS_BLANK(order)) {
            throw new IllegalArgumentException(ExceptionMessage.CONTAINS_BLANK.toString());
        }
        if (isIncorrectFormat(order)) {
            throw new IllegalArgumentException(ExceptionMessage.WRONG_FORMAT.toString());
        }
    }

    private boolean isIncorrectFormat(String order) {
        Matcher matcher = PATTERN.matcher(order);
        return !matcher.matches();
    }

    private void validateNumberRange(Map<String, String> orders) throws IllegalArgumentException {
        for (Map.Entry<String, String> entry : orders.entrySet()) {
            try {
                Integer.parseInt(entry.getValue());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(ExceptionMessage.OUT_OF_INT.toString());
            }
        }
    }

    private void validateContent(Map<String, Integer> orders, Inventory inventory)
            throws IllegalArgumentException {
        if (doNotContainsProduct(orders, inventory)) {
            throw new IllegalArgumentException(ExceptionMessage.DO_NOT_HAVE_PRODUCT.toString());
        }
        if (exceedStock(orders, inventory)) {
            throw new IllegalArgumentException(ExceptionMessage.EXCEED_STOCK.toString());
        }
        if (zeroQuantity(orders)) {
            throw new IllegalArgumentException(ExceptionMessage.BUY_ZERO_QUANTITY.toString());
        }
    }

    private boolean doNotContainsProduct(Map<String, Integer> orders, Inventory inventory) {
        for (Map.Entry<String, Integer> entry : orders.entrySet()) {
            String name = entry.getKey();
            if (inventory.doNotContainsProduct(name)) {
                return true;
            }
        }
        return false;
    }

    private boolean exceedStock(Map<String, Integer> orders, Inventory inventory) {
        for (Map.Entry<String, Integer> entry : orders.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            if (inventory.exceedStockOfProductNameAndQuantity(productName, quantity)) {
                return true;
            }
        }
        return false;
    }

    private boolean zeroQuantity(Map<String, Integer> orders) {
        for (Map.Entry<String, Integer> entry : orders.entrySet()) {
            int quantity = entry.getValue();
            if (quantity == 0) {
                return true;
            }
        }
        return false;
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

    private Map<String, String> getOrders(String orderLine) {
        Map<String, String> cart = new HashMap<>();
        String[] orderLines = orderLine.split(",");
        for (String rawOrderLine : orderLines) {
            putOrder(cart, rawOrderLine);
        }
        return cart;
    }

    private void putOrder(Map<String, String> orders, String rawOrderLine) {
        String trimmedOrder = rawOrderLine.substring(1, rawOrderLine.length() - 1);
        String[] nameAndQuantity = trimmedOrder.split("-");
        String name = nameAndQuantity[NAME_INDEX];
        String quantity = nameAndQuantity[QUANTITY_INDEX];
        orders.put(name, quantity);
    }


    private Map<Product, OrderProductQuantity> getOrderProducts(Map<String, Integer> namesAndQuantity,
                                                                Inventory inventory) {
        Map<Product, OrderProductQuantity> cart = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : namesAndQuantity.entrySet()) {
            String name = entry.getKey();
            Product product = inventory.getProduct(name);
            int totalQuantity = entry.getValue();
            OrderProductQuantity quantity = OrderProductQuantity.init(totalQuantity);
            cart.put(product, quantity);
        }
        return cart;
    }

}
