package store.model;

public class Quantity {

    private int regular;

    private int promotion;

    public Quantity() {
        this.regular = 0;
        this.promotion = 0;
    }
    
    public Quantity(int regular, int promotion) {
        this.regular = 0;
        this.promotion = 0;
    }
    
    public static Quantity getQuantity(Product product, int purchaseQuantity) {
        Quantity quantity = new Quantity();
        if (product.containsPromotion()) {
            quantity.setPromotion(purchaseQuantity);
        }
        if (product.notContainsPromotion()) {
            quantity.setRegular(purchaseQuantity);
        }
        return quantity;
    }
    
    public int getRegular() {
        return regular;
    }

    public void setRegular(int regular) {
        this.regular = regular;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }
    
    public void setPromotionSale(Stock stock, Object promotionBonus) {
        int promotionStock = stock.getPromotion();
        if (this.promotion > promotionStock) {
            int smallChange = this.promotion - promotionStock;
            this.regular += smallChange;
        }
    }
    
    @Override
    public String toString() {
        return "Quantity{" +
                "regular=" + regular +
                ", promotion=" + promotion +
                '}';
    }
    
    public int getTotal() {
        return this.promotion + this.regular;
    }
}
