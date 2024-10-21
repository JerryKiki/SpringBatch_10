package com.koreait.exam.springbatch_10.app.order.entity;

import com.koreait.exam.springbatch_10.app.base.entity.BaseEntity;
import com.koreait.exam.springbatch_10.app.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Table(name = "product_order")
public class Order extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);

        orderItems.add(orderItem);
    }

    public int calculatePayPrice() {
        int payPrice = 0;

        for (OrderItem orderItem : orderItems) {
            payPrice += orderItem.calculatePayPrice();
        }

        return payPrice;
    }

    public void setPaymentDone() {
        for (OrderItem orderItem : orderItems) {
            orderItem.setPaymentDone();
        }
    }

    public int getPayPrice() {
        int payPrice = 0;

        for (OrderItem orderItem : orderItems) {
            payPrice += orderItem.getPayPrice();
        }

        return payPrice;
    }

    public void setRefundDone() {
        for(OrderItem orderItem : orderItems) {
            orderItem.setRefundDone();
        }
    }
}

//@Builder.Default
//기본값을 지정. 이렇게 명시해주면, List의 원래 기본값인 null이 아니라
// orderItems = new ArrayList<>(); 이거 자체가 기본값이 된다. 어떻게 보면 null 방지.
//얘랑 연결될, 매핑이 될 놈을 mappedBy에 써줌. orderItems는 order에 의해 매핑될것이다...
//헷갈리니까 써두기. 얘는 Order class니깐, Order의 입장인 One이 먼저 나오는 것으로 추정.
//ManyToOne은 OrderItem 클래스에 써있다.

//@OneToMany, @ManyToOne => 두 클래스 간의 관계가 존재할 때, 어떤 관계인가를 명시
//@ManyToOne은 관계가 존재한다면 필수. OneToMany는 필수는 아님
//@ManyToOne이 써있는 쪽이 주체가 됨 => Many(다수)쪽이 주체가 됨
//@ManyToOne : 다대일 관계. 다수의 엔티티가 하나의 엔티티에 연결되는 상황에서 필수.
// => 다대일 관계를 명시하지 않으면 데이터베이스 쪽에서 모른다.
//ex) OrderItem은 Order에 속한다. => OrderItem이 Order를 참조해야함. 그래서 @ManyToOne.
//@OneToMany : 일다대 관계. 하나의 엔티티가 여러 하위 엔티티를 가질 때 사용.
// => 필수는 아니지만, 한쪽은 ManyToOne인데 OneToMany가 안 써있으면 파악이 힘들기에 관계를 명확히 하기 위해 써준다.
//ex) 하나의 Order가 여러 OrderItem을 가진다.
//대량 데이터를 다루는 DB를 짰는데 문제가 있는 것 같다면 이 관계쪽을 의심해보는 것이 좋다
