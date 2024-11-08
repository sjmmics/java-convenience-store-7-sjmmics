package store.exception;

public class OutOfPromotionStockException extends RuntimeException {
    
    public OutOfPromotionStockException() {
    }
    
    public OutOfPromotionStockException(String message) {
        super(message);
    }
}
