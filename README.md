# java-convenience-store-precourse

* 쇼핑 카드
    * 상품 이름
    * 상품 가격
    * 상품 프로모션
    * 상품 수량(프로모션, 일반)
    * 증정품
    * 멤버쉽 할인

* 프로모션 할인 적용
    * 해당 상품 프로모션이 있는지 확인
    * 프로모션 기간인지 확인
      * 기간이면 프로모션 개수 단위인지 확인(구매 수량 % (프로모션 구매 + 보너스) == 0)
        * 나누어 안 떨어지면 프로모션 단위로 떨어지는 수량만큼 프로모션 재고가 있는지 확인
          * 재고가 있으면 증정품을 받을 것인지 확인
            * 받는다고 하면 증정품 추가
            * 안 받는다고 하면 증정품 추가 X
          * 재고가 없으면 프로모션 재고 먼저 차감, 나머지는 일반으로 차감
        * 나누어 떨어지면 수량만큼 재고가 있는지 확인
          * 재고가 있으면 프로모션 재고 차감
          * 재고가 없으면 프로모션 재고 외 나머지는 일반으로 구매할 건인지 확인
            * 일반으로 구매한다고 하면 프로모션 재고 먼저 차감, 나머지는 일반으로 차감
            * 일반으로 구매 안 한다고 하면 종료
      * 기간이 아니면 일반 재고부터 차감

* 멤버쉽 할인 적용
    * 멤버쉽 할인을 받을지 선택
    * 멤버쉽 할인을 기준 금액 합산(프로모션 제품 제외)
    * 기준 금액의 30% (최대한도 8,000원) 할인

* 쇼핑 카드
    * 상품 제목 : 수량(프로모션 적용, 미적용)
    * 가격 : 상품별, 총가격
    * 멤버쉽 할인 적용
