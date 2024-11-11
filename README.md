우아한테크코스 7기 프리코스 4주차 과제 : 편의점(java-convenience-store-precourse)
===============================================================================

기능 목록
---------
* InputView: 입력과 관련된 안내 메시지를 출력하고 입력을 받아서 StoreController에 전달한다.
  * 입력값이 Y 또는 N이어야 하는 경우 입력받은 문자열을 검증해서 잘못된 형식이면 IllegalArgumentException를 호출하고, 예외 메시지를 출력하고 다시 입력을 받는다.
* OutputView: StoreController로부터 전달받은 결과값을 출력한다.
* StoreController: 프로그램의 메인 흐름을 제어하고, View와 Service 계층을 매개하는 역할을 한다. 
  * InputView로부터 모델 생성에 필요한 값을 전달받아서 모델 객체를 생성하고, StoreServcie에 전달한다.
  * StoreService에서 생성한 결과 값을 요청해서 전달받고 이를 OutputView에 전달한다.
* StoreService: controller에서 전달 받은 모델을 가지고 주요 비즈니스 로직을 실행하며, 결과값을 Repository에 저장하거나, Controller에 반환한다.
* Repository: 주요 모델를 저장한다.
* Inventory: 편의점 상품 재고 상황을 보관한다.
* Stock: 상품 재고 상황(프로모션 재고와 일반재고)을 저장한다.
* Product: 편의점에서 파는 상품 정보(이름, 가격, 적용되는 프로모션 정보)를 담는다.
* Promotion: 프로모션 할인 정보(이름, 보너스 수량, 행사 시작일, 종료일)를 담는다.
* Promotions: Promotion 객체를 컬렉션으로 보관한다.
* OrderDetails: 주문 정보(주문 상품, 수량)를 저장한다.
* OrderDetailsFactory: 주문 정보 객체(OrderDetails)를 만드는 팩토리 클래스이다. 주문 정보 문자열을 받아서 검증하며 형식이 맞지 않으면 IllegalException를 호출한다. 
* OrderQuantityOption: 주문한 상품 프로모션 할인과 관련된 수량 조절 선택지를 담는다. 옵션 종류(OptionCase)는 주문한 상품이 프로모션 재고가 없는 경우, 프로모션 적용 보너스 상품을 추가로 받을 수 있는 주문인 경우 두 가지이다.
  * 상품별 경우와, 이를 안내할 메시지, 고객의 선택을 저장한다.
* OrderQuantityOptions: OrderQuantityOption 객체를 컬렉션으로 보관한다.
* QuantityOptionsFactory: 주문 정보(OrderDetails)와 재고 현황(Inventory)과 주문시간을 바탕으로 비교해서 주문 중에 수량을 조절해야 하는 경우(OptionCase)가 있는지 확인하고 OrderQuantityOptions 객체를 생성한다.
* OrderProductQuantity: 주문한 상품 수량을 저장한다. 수량 정보는 총 구매수량, 프로모션 재고 차감 수량, 일반 재고 차감 수량으로 이루어진다.
* OrderQuantityAdjuster: 주문한 상품 수량 조절 선택지(OrderQuantityOptions)에 대한 주문자의 선택을 바탕으로 수량을 조절하며, 주문 수량(OrderProductQuantity)과 재고 현황(Inventory)를 바탕으로 주문 수량에서 프로모션 재고 차감분과 일반 재고 차감분을 갱신한다.
* PromotionDiscountDetail: 주문 상품 별 프로모션 할인 적용 정보(프로모션 이름, 증정품 수량, 증정품 가격 합)를 저장한다. 
* PromotionDiscountDetails: PromotionDiscountDetail를 컬렉션으로 보관한다.
* MembershipDiscountDetail: 주문에 대한 멤버십할인 금액를 저장한다.
* Receipt: 고객에게 보여줄 영수증을 담당하는 클래스, 주문 정보와 할인 내역을 표시한다.
* InventoryInitializer: 재고 파일에서 재고 현황을 읽어서 초기화한다.
* PromotionInitializer: 프로모션 파일에서 상품별 프로모션 정보를 읽어서 초기화한다.
* StringValidator: 여러 클래스에서 공통으로 쓰는 문자열 검증 기능을 담당한다.
* Constants: 여러 클래스에서 공통으로 쓰는 상수를 저장한다.
* ExceptionMessage: 예외 메시지를 저장한다.
* KoreanStringFormatter: 자리수에 맞추어 정렬된 문자열을 만든다.