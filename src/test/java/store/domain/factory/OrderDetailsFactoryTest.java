package store.domain.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import store.model.inventory.Inventory;
import store.model.inventory.Product;
import store.model.inventory.Stock;
import store.model.order.OrderDetailsFactory;
import store.model.discount.Promotion;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static store.util.ExceptionMessage.*;

public class OrderDetailsFactoryTest {
    
    private OrderDetailsFactory factory;
    
    private Inventory inventory;
    
    private final static LocalDate PROMOTION_START_DATE = LocalDate.of(2024, 1,1);
    
    private final static LocalDate PROMOTION_END_DATE = LocalDate.of(2024, 12, 31);
    
    private final static Promotion sparkingTwoPlusOne =
            new Promotion("탄산2+1", PROMOTION_START_DATE, PROMOTION_END_DATE, 2);
    
    private final Map<String, Promotion> nameByPromotion = Map.of("탄산2+1", sparkingTwoPlusOne);
    
    private final static Product COLA = new Product("콜라", 1000, sparkingTwoPlusOne);
    
    private final static Product WATER = new Product("물", 500, null);
    
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
    @DisplayName("입력 값 예외 종류 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"[콜라-1],[콜라-1],[콜라-1]", "\n", "[콜 라-3]", "[콜라--3개]", "콜라-3", "[콜라-1,100]",
            "[콜라-5], [물-1]", "[콜라-100000000000000000000000]"})
    void exceptionKindTest(String input) {
        // 예외 종류 확인
        assertThrows(IllegalArgumentException.class, () -> factory.create(input, inventory));
    }
    
    @DisplayName("입력 값 예외 메시지 테스트")
    @ParameterizedTest
    @MethodSource("generateExceptionMessageByInput")
    void exceptionMessageTest(String input, String message) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.create(input, inventory));
        
        assertEquals(message, exception.getMessage());
    
    }
    
    static Stream<Arguments> generateExceptionMessageByInput() {
        return Stream.of(
                Arguments.of("", EMPTY_INPUT.toString()),
                Arguments.of("[콜라-5], [물-1]", CONTAINS_BLANK.toString()),
                Arguments.of("[콜라-999999999999999]", OUT_OF_INT.toString()),
                Arguments.of("[없는물건-10]", DO_NOT_HAVE_PRODUCT.toString()),
                Arguments.of("[콜라-1000]", EXCEED_STOCK.toString()),
                Arguments.of("[콜라-0]", BUY_ZERO_QUANTITY.toString()),
                Arguments.of("[콜라-1],[콜라-1],[콜라-1]", DUPLICATE_NAMES.toString()),
                Arguments.of("콜라-1", WRONG_FORMAT.toString())
        );
    }

}
