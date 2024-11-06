package store.model;

public class Quantity {

    private int regular;

    private int promotion;

    public Quantity() {
        this.regular = 0;
        this.promotion = 0;
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
}
