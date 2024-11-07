package store.model;

public class Stock {
    
    private static final String SOLD_OUT = "재고 없음";
    
    private int regular;
    
    private int promotion;
    
    public Stock() {
        this.regular = 0;
        this.promotion = 0;
    }
    
    public void addOfQuantityAndPromotion(int productQuantity, Promotion promotion) {
        if (promotion == null) {
            regular += productQuantity;
        }
        if (promotion != null) {
            this.promotion += productQuantity;
        }
    }
    
    public String getPromotionString() {
        if (promotion == 0) {
            return SOLD_OUT;
        }
        return promotion + "개";
    }
    
    public String getRegularString() {
        if (regular == 0) {
            return SOLD_OUT;
        }
        return regular + "개";
    }

    public int getTotal() {
        return regular + promotion;
    }
    
    public int getRegular() {
        return regular;
    }
    
    public int getPromotion() {
        return promotion;
    }
}
