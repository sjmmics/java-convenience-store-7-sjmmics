package store.domain.factory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.inventory.Inventory;
import store.model.inventory.Product;
import store.model.inventory.Stock;
import store.model.order.OrderDetails;
import store.model.orderquantity.OrderProductQuantity;
import store.model.orderquantity.OrderQuantityOption;
import store.model.orderquantity.OrderQuantityOptions;
import store.model.orderquantity.QuantityOptionsFactory;
import store.model.discount.Promotion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static store.model.orderquantity.OrderQuantityOption.OptionCase.NOT_CONTAIN_BONUS;
import static store.model.orderquantity.OrderQuantityOption.OptionCase.OUT_OF_PROMOTION_STOCK;

public class OrderProductQuantityOptionsFactoryTest {
    
    private static final int QUANTITY_EXCEED_PROMOTION_STOCK_THREE = 13;
    
    private static final int COLA_PROMOTION_STOCK = 10;
    
    private static final int COLA_REGULAR_STOCK = 8;
    
    private static final int QUANTITY_NOT_INCLUDE_ALL_BONUS = 2;
    
    private final LocalDate promotionStartDate = LocalDate.of(2024, 1, 1);
    
    private final LocalDate promotionEndDate = LocalDate.of(2024, 12, 31);
    
    private final Promotion starkingTwoPlusOne =
            new Promotion("탄산2+1", promotionStartDate, promotionEndDate, 2);
    
    private final Product cola = new Product("콜라", 1000, starkingTwoPlusOne);
    
    private Inventory inventory;
    
    @AfterEach
    void clear() {
        this.inventory = null;
    }
    
    private Map<Product, OrderProductQuantity> getQuantityByProduct(int quantity) {
        Product cola = new Product("콜라", 1000, starkingTwoPlusOne);
        OrderProductQuantity colaOrderQuantity = OrderProductQuantity.init(quantity);
        return Map.of(cola, colaOrderQuantity);
    }
    
    void setUpInventoryColaStockPromotionTenRegularEight() {
        setUpInventoryColaStock(COLA_PROMOTION_STOCK, COLA_REGULAR_STOCK);
    }
    
    private void setUpInventoryColaStock(int promotion, int regular) {
        Stock colaStock = new Stock();
        colaStock.setPromotion(promotion);
        colaStock.setRegular(regular);
        Map<Product, Stock> stockByProduct = Map.of(cola, colaStock);
        this.inventory = new Inventory(stockByProduct);
    }
    
    @Test
    @DisplayName("구매 옵션 테스트 프로모션 재고 수량 초과 주문")
    void orderExceedPromotionStockCaseTest() {
        // 재고 현황 콜라 프로모션 재고 10개, 일반 재고 8개
        setUpInventoryColaStockPromotionTenRegularEight();
        OrderQuantityOptions options =
                getOptionsFromQuantity(QUANTITY_EXCEED_PROMOTION_STOCK_THREE);
        for (OrderQuantityOption option : options) {
            assertEquals(option.getOptionCase(), OUT_OF_PROMOTION_STOCK);
            assertNotEquals(option.getOptionCase(), NOT_CONTAIN_BONUS);
        }
    }
    
    @Test
    @DisplayName("구매 옵션 테스트 프로모션 재고 수량 밑 주문")
    void orderNotExceedPromotionStockCaseTest() {
        // 재고 현황 콜라 프로모션 재고 10개, 일반 재고 8개
        setUpInventoryColaStockPromotionTenRegularEight();
        OrderQuantityOptions options2 =
                getOptionsFromQuantity(QUANTITY_EXCEED_PROMOTION_STOCK_THREE - 3);
        for (OrderQuantityOption option : options2) {
            assertNull(option.getOptionCase());
        }
    }
    
    @Test
    @DisplayName("재고 수량 초과분 수량 테스트")
    void orderExceedPromotionStockDeductionCountTest() {
        // 재고 현황 콜라 프로모션 재고 10개, 일반 재고 8개
        setUpInventoryColaStockPromotionTenRegularEight();
        OrderQuantityOptions options =
                getOptionsFromQuantity(QUANTITY_EXCEED_PROMOTION_STOCK_THREE);
        for (OrderQuantityOption option : options) {
            assertEquals(option.getShortage(), 3);
        }
    }
    
    @Test
    @DisplayName("프로모션 증정품 불포함 주문 종류 테스트")
    void orderNotIncludeBonusCaseTest() {
        // 재고 현황 콜라 프로모션 재고 10개, 일반 재고 8개
        setUpInventoryColaStockPromotionTenRegularEight();
        OrderQuantityOptions options =
                getOptionsFromQuantity(QUANTITY_NOT_INCLUDE_ALL_BONUS);
        for (OrderQuantityOption option : options) {
            assertEquals(option.getOptionCase(), NOT_CONTAIN_BONUS);
            assertNotEquals(option.getOptionCase(), OUT_OF_PROMOTION_STOCK);
        }
    }
    
    @Test
    @DisplayName("프로모션 증정품 불포함 주문 부족 수량 테스트")
    void orderNotIncludeBonusCountTest() {
        // 재고 현황 콜라 프로모션 재고 10개, 일반 재고 8개
        setUpInventoryColaStockPromotionTenRegularEight();
        OrderQuantityOptions options =
                getOptionsFromQuantity(QUANTITY_NOT_INCLUDE_ALL_BONUS);
        for (OrderQuantityOption option : options) {
            assertEquals(option.getShortage(), 1);
        }
    }
    
    @Test
    @DisplayName("증정품이 모두 포함되지 않은 수량으로 주문했지만, " +
            "프로모션 수량이 부족한 경우 테스트")
    void notIncludeAllBonusButOutOfPromotionStockTest() {
        setUpInventoryColaStock(8, 8);
        OrderQuantityOptions options = 
                getOptionsFromQuantity(8);
        for (OrderQuantityOption option : options) {
            assertNotEquals(option.getOptionCase(), NOT_CONTAIN_BONUS);
            assertNull(option.getOptionCase());
        }
    }
    
    OrderQuantityOptions getOptionsFromQuantity(int quantity) {
        LocalDateTime orderTime = LocalDateTime.of(2024, 11, 11, 10, 10);
        Map<Product, OrderProductQuantity> orderColaOverPromotionStock = getQuantityByProduct(quantity);
        OrderDetails orderDetails = new OrderDetails(orderColaOverPromotionStock);
        QuantityOptionsFactory optionsFactory = new QuantityOptionsFactory(orderDetails);
        return optionsFactory.getOptions(this.inventory, orderTime);
    }
    
}
