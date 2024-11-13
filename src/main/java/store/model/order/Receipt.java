package store.model.order;

import store.model.discount.MembershipDiscountDetail;
import store.model.inventory.Product;
import store.model.orderquantity.OrderProductQuantity;
import store.model.discount.PromotionDiscountDetail;
import store.model.discount.PromotionDiscountDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static store.util.KoreanStringFormatter.CONVERT_LEFT;
import static store.util.KoreanStringFormatter.CONVERT_RIGHT;
import static store.util.KoreanStringFormatter.CONVERT_RIGHT_WITH_MINUS;

public class Receipt {

    private final List<String> receipt;

    public Receipt(OrderDetails orderDetails, PromotionDiscountDetails promotionDiscountDetails,
                   MembershipDiscountDetail membershipDiscountDetail) {
        List<String> receipt = new ArrayList<>();
        addOrderDetails(receipt, orderDetails);
        addPromotionDiscount(receipt, promotionDiscountDetails);
        addSummaryPayAndPromotionDiscount(receipt, orderDetails, promotionDiscountDetails);
        addMembershipDiscount(receipt, membershipDiscountDetail);
        String moneyToPayLine =
                getMoneyToPayLine(orderDetails, promotionDiscountDetails, membershipDiscountDetail);
        receipt.add(moneyToPayLine);
        this.receipt = receipt;
    }

    private void addOrderDetails(List<String> receipt, OrderDetails orderDetails) {
        receipt.add(Message.HEADER);
        receipt.add(Message.ORDER_DETAILS_HEADER);
        for (Map.Entry<Product, OrderProductQuantity> entry : orderDetails.getEntrySet()) {
            Product product = entry.getKey();
            OrderProductQuantity quantity = entry.getValue();
            int totalAmount = product.getUnitPrice() * quantity.getTotal();
            String orderDetailLine =
                    Message.GET_ORDER_DETAILS(product.getName(), quantity.getTotal(), totalAmount);
            receipt.add(orderDetailLine);
        }
    }

    private String getMoneyToPayLine(OrderDetails orderDetails,
                                     PromotionDiscountDetails promotionDiscountDetails,
                                     MembershipDiscountDetail membershipDiscountDetail) {
        int moneyBeforeDiscount = orderDetails.getTotalAmount();
        int totalAmountPromotion = promotionDiscountDetails.getTotalAmount();
        int totalAmountMembership = membershipDiscountDetail.getAmount();
        int moneyToPay = moneyBeforeDiscount - totalAmountPromotion - totalAmountMembership;
        return Message.GET_MONEY_TO_PAY(moneyToPay);
    }

    private void addMembershipDiscount(List<String> receipt,
                                       MembershipDiscountDetail membershipDiscountDetail) {
        int discountAmount = membershipDiscountDetail.getAmount();
        String membershipDiscountLine = Message.GET_MEMBERSHIP_DISCOUNT_LINE(discountAmount);
        receipt.add(membershipDiscountLine);
    }

    private void addSummaryPayAndPromotionDiscount(List<String> receipt,
                                                   OrderDetails orderDetails,
                                                   PromotionDiscountDetails discountDetails) {
        int totalAmount = orderDetails.getTotalAmount();
        int totalQuantity = orderDetails.getTotalQuantity();
        int promotionAmount = discountDetails.getTotalAmount();
        receipt.add(Message.PAY_AND_DISCOUNT_HEADER);
        String totalAmountLine = Message.GET_TOTAL_AMOUNT(totalQuantity, totalAmount);
        String promotionDiscountSummary = Message.GET_PROMOTION_SUMMARY(promotionAmount);
        receipt.add(totalAmountLine);
        receipt.add(promotionDiscountSummary);
    }

    private void addPromotionDiscount(List<String> receipt, PromotionDiscountDetails discountDetails) {
        receipt.add(Message.PROMOTION_DETAILS_HEADER);
        if (discountDetails.isEmpty()) {
            receipt.add(Message.NOT_CONTAINS_PROMOTION);
            return;
        }
        for (PromotionDiscountDetail detail : discountDetails) {
            String detailLine = Message.GET_PROMOTION_DETAIL(detail.getProductName(),
                    detail.getBonusCount());
            receipt.add(detailLine);
        }
    }

    @Override
    public String toString() {
        return String.join("\n", receipt);
    }

    static final class Message {

        static final int LEFT_BLANK_SIZE = 22;

        static final int MIDDLE_BLANK_SIZE = 6;

        static final int RIGHT_BLANK_SIZE = 10;

        static final String HEADER = "==============W 편의점================";

        static final String ORDER_DETAILS_HEADER = CONVERT_LEFT("상품명", LEFT_BLANK_SIZE)
                + CONVERT_LEFT("수량", MIDDLE_BLANK_SIZE)
                + CONVERT_RIGHT("금액", RIGHT_BLANK_SIZE);

        static final String PROMOTION_DETAILS_HEADER = "==============증     정===============";

        static final String NOT_CONTAINS_PROMOTION = "증정품 없음";

        static final String PAY_AND_DISCOUNT_HEADER = "======================================";

        public static String GET_ORDER_DETAILS(String name, int totalQuantity, int totalAmount) {
            return CONVERT_LEFT(name, LEFT_BLANK_SIZE)
                    + CONVERT_LEFT(totalQuantity, MIDDLE_BLANK_SIZE)
                    + CONVERT_RIGHT(totalAmount, RIGHT_BLANK_SIZE);
        }

        public static String GET_PROMOTION_DETAIL(String productName, int bonusCount) {
            return CONVERT_LEFT(productName, LEFT_BLANK_SIZE) + bonusCount;
        }

        public static String GET_MONEY_TO_PAY(int moneyToPay) {
            return CONVERT_LEFT("내실돈", LEFT_BLANK_SIZE + MIDDLE_BLANK_SIZE)
                    + CONVERT_RIGHT(moneyToPay, RIGHT_BLANK_SIZE);
        }

        public static String GET_MEMBERSHIP_DISCOUNT_LINE(int discountAmount) {
            return CONVERT_LEFT("멤버십할인", LEFT_BLANK_SIZE + MIDDLE_BLANK_SIZE)
                    + CONVERT_RIGHT_WITH_MINUS(discountAmount, RIGHT_BLANK_SIZE);
        }

        public static String GET_TOTAL_AMOUNT(int totalQuantity, int totalAmount) {
            return CONVERT_LEFT("총구매액", LEFT_BLANK_SIZE)
                    + CONVERT_LEFT(totalQuantity, MIDDLE_BLANK_SIZE)
                    + CONVERT_RIGHT(totalAmount, RIGHT_BLANK_SIZE);
        }

        public static String GET_PROMOTION_SUMMARY(int promotionAmount) {
            return CONVERT_LEFT("행사할인", LEFT_BLANK_SIZE + MIDDLE_BLANK_SIZE)
                    + CONVERT_RIGHT_WITH_MINUS(promotionAmount, RIGHT_BLANK_SIZE);
        }

    }
    
}