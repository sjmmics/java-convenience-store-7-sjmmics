package store.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.inventory.Inventory;
import store.model.inventory.Product;
import store.model.inventory.Stock;
import store.model.order.OrderDetails;
import store.model.order.OrderDetailsFactory;
import store.model.orderquantity.OrderProductQuantity;
import store.model.discount.Promotion;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDetailsTest {
    
    private OrderDetailsFactory factory;
    
    private Inventory inventory;
    
    private final static LocalDate PROMOTION_START_DATE = LocalDate.of(2024, 1, 1);
    
    private final static LocalDate PROMOTION_END_DATE = LocalDate.of(2024, 12, 31);
    
    private final static Promotion SPARKING_TWO_PLUS_ONE =
            new Promotion("탄산2+1", PROMOTION_START_DATE, PROMOTION_END_DATE, 2);
    
    private final static int COLA_PRICE = 1000;
    
    private final static int WATER_PRICE = 500;
    
    private final static Product COLA = new Product("콜라", COLA_PRICE, SPARKING_TWO_PLUS_ONE);
    
    private final static Product WATER = new Product("물", WATER_PRICE, null);
    
    @BeforeEach
    void setUp() {
        this.factory = new OrderDetailsFactory();
        Stock colaStock = new Stock();
        colaStock.setPromotion(10);
        colaStock.setRegular(8);
        Stock waterStock = new Stock();
        waterStock.setRegular(10);
        Map<Product, Stock> productStockMap = Map.of(COLA, colaStock, WATER, waterStock);
        this.inventory = new Inventory(productStockMap);
    }
    
    @Test
    @DisplayName("총 주문 금액 테스트")
    void totalAmountTest() {
        String orderLine = "[콜라-10],[물-7]";
        OrderDetails orderDetails = factory.create(orderLine, inventory);
        assertEquals(orderDetails.getTotalAmount(), COLA_PRICE * 10 + WATER_PRICE * 7);
        assertNotEquals(orderDetails.getTotalAmount(), COLA_PRICE * 7 + WATER_PRICE * 10);
    }
    
    @Test
    @DisplayName("총 주문 수량 테스트")
    void totalCountTest() {
        String orderLine = "[콜라-10],[물-7]";
        OrderDetails orderDetails = factory.create(orderLine, inventory);
        assertEquals(orderDetails.getTotalQuantity(), 10 + 7);
    }
    
    @Test
    @DisplayName("주문 상품 항목 테스트")
    void productContainsTest() {
        String orderLine = "[콜라-3]";
        OrderDetails orderDetails = factory.create(orderLine, inventory);
        Set<Product> products = new HashSet<>();
        for (Map.Entry<Product, OrderProductQuantity> entry : orderDetails.getEntrySet()) {
            Product product = entry.getKey();
            products.add(product);
        }
        assertTrue(products.contains(COLA));
        assertFalse(products.contains(WATER));
    }
    
    @Test
    @DisplayName("주문 상품 수량 테스트")
    void productOrderQuantityTest() {
        String orderLine = "[콜라-3],[물-1]";
        OrderDetails orderDetails = factory.create(orderLine, inventory);
        for (Map.Entry<Product, OrderProductQuantity> entry : orderDetails.getEntrySet()) {
            Product product = entry.getKey();
            OrderProductQuantity quantity = entry.getValue();
            if (product.equalsName("콜라")) {
                assertEquals(quantity.getTotal(), 3);
            }
        }
    }
}
