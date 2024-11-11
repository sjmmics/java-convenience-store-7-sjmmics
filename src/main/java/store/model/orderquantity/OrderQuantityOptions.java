package store.model.orderquantity;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/** 재고 및 프로모션 할인에 따른 주문 변경 선택지(OrderQuantityOptions)를 컬렉션으로 보관하는 클래스 */
public class OrderQuantityOptions implements Iterable<OrderQuantityOption> {

    private final List<OrderQuantityOption> options;

    public OrderQuantityOptions(List<OrderQuantityOption> options) {
        this.options = options;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (OrderQuantityOption option : options) {
            sb.append(option);
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public Iterator<OrderQuantityOption> iterator() {
        return new PromotionPurchaseOptionsIterator();
    }

    private class PromotionPurchaseOptionsIterator implements Iterator<OrderQuantityOption> {

        private int index;

        public PromotionPurchaseOptionsIterator() {
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < options.size();
        }

        @Override
        public OrderQuantityOption next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            OrderQuantityOption option = options.get(index);
            index++;
            return option;
        }
    }

}
