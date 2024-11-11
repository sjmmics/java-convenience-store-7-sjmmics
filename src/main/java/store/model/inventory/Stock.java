package store.model.inventory;

import store.model.orderquantity.OrderProductQuantity;

/** 상품 별 재고에 해당하는 클래스 */
public class Stock {
    
    private static final String SOLD_OUT = "재고 없음";
    
    private int regular;
    
    private int promotion;
    
    public Stock() {
        this.regular = 0;
        this.promotion = 0;
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

    public int getPromotion() {
        return promotion;
    }

    public void setBySale(OrderProductQuantity saleQuantity) {
        int promotionSale = saleQuantity.getPromotion();
        int regularSale = saleQuantity.getRegular();
        this.promotion -= promotionSale;
        this.regular -= regularSale;
    }

    public void setRegular(int quantity) {
        this.regular = quantity;
    }

    public void setPromotion(int quantity) {
        this.promotion = quantity;
    }
}
