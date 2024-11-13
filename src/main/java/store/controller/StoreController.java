package store.controller;

import camp.nextstep.edu.missionutils.DateTimes;

import store.model.inventory.Inventory;
import store.model.order.OrderDetails;
import store.model.order.OrderDetailsFactory;
import store.model.orderquantity.OrderQuantityOption;
import store.model.orderquantity.OrderQuantityOptions;
import store.model.order.Receipt;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.time.LocalDateTime;

/**
 * 프로그램의 메인 흐름을 제어하고, View와 Service 계층을 매개하는 역할을 하는 클래스
 * InputView로부터 모델 생성에 필요한 값을 전달받아서 모델 객체를 생성하고, StoreServcie에 전달한다.
 * StoreService에서 생성한 결과 값을 요청해서 전달받고 이를 OutputView에 전달한다.
 */
public class StoreController {

    private static final String YES = "Y";

    private final InputView inputView;
    
    private final OutputView outputView;
    
    private final StoreService service;
    
    public StoreController(InputView inputView, OutputView outputView, StoreService service) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.service = service;
    }
    
    public void run() {
        service.initPromotionsAndInventory();
        boolean keepOpen = true;
        while (keepOpen) {
            keepOpen = storeOpen(DateTimes.now());
        }
    }

    private boolean storeOpen(LocalDateTime orderTime) {
        getOrderAndSave();
        OrderQuantityOptions options = service.getOrderQuantityOptions(orderTime);
        setQuantityOptionDecisions(options);
        service.adjustQuantity(options, orderTime);
        discountAndPrintReceipt(orderTime);
        service.deductInventoryBySale();
        return doesKeepOrder();
    }
    
    private void getOrderAndSave() {
        OrderDetailsFactory factory = new OrderDetailsFactory();
        Inventory inventory = service.getInventory();
        OrderDetails orderDetails = getOrderDetails(factory, inventory);
        service.saveOrderDetails(orderDetails);
    }
    
    private OrderDetails getOrderDetails(OrderDetailsFactory factory, Inventory inventory) {
        while (true) {
            try {
                String orderLine = inputView.getOrder(inventory);
                return factory.create(orderLine, inventory);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void setQuantityOptionDecisions(OrderQuantityOptions options) {
        for (OrderQuantityOption option : options) {
            String decisionLine = inputView.getOptionDecision(option);
            if (decisionLine.equals(YES)) {
                option.setDecisionTrue();
            }
        }
    }

    private void discountAndPrintReceipt(LocalDateTime orderTime) {
        service.discountPromotion(orderTime);
        String membershipDiscountChoice = inputView.getMemberShipDiscountDecision();
        boolean wantMembershipDiscount = doesWantMembershipDiscount(membershipDiscountChoice);
        service.discountMembership(wantMembershipDiscount);
        Receipt receipt = service.getReceipt();
        outputView.printReceipt(receipt);
    }

    private boolean doesWantMembershipDiscount(String membershipDiscountChoice) {
        return membershipDiscountChoice.equals(YES);
    }
    
    private boolean doesKeepOrder() {
        String keepOpenLine = inputView.getKeepOpenLine();
        return keepOpenLine.equals(YES);
    }

}
