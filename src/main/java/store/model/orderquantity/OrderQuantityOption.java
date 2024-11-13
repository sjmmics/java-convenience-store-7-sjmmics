package store.model.orderquantity;

import store.model.inventory.Product;

/** 재고 및 프로모션 할인에 따른 주문 변경 선택지(OrderQuantityOptions)를 담당하는 클래스 */
public class OrderQuantityOption {

    private final Product product;

    private final OrderQuantityOption.OptionCase OptionCase;

    private final int shortage;

    private boolean decision;

    public OrderQuantityOption(Product product, OrderQuantityOption.OptionCase OptionCase, int shortage) {
        this.product = product;
        this.OptionCase = OptionCase;
        this.shortage = shortage;
    }

    public boolean isDecisionYes() {
        return this.decision;
    }

    public Product getProduct() {
        return this.product;
    }

    public int getShortage() {
        return this.shortage;
    }

    public OptionCase getOptionCase() {
        return this.OptionCase;
    }

    public void setDecisionTrue() {
        this.decision = true;
    }

    @Override
    public String toString() {
        return String.format(OptionCase.message, product.getName(), shortage);
    }

    public enum OptionCase {

        OUT_OF_PROMOTION_STOCK("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. " +
                "그래도 구매하시겠습니까? (Y/N)"),

        NOT_CONTAIN_BONUS("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. " +
                "추가하시겠습니까? (Y/N)");

        final String message;

        OptionCase(String message) {
            this.message = message;
        }

        public boolean isEquals(OptionCase optionCase) {
            return this.equals(optionCase);
        }

        @Override
        public String toString() {
            return this.message;
        }

    }

}
