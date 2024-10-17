package com.koreait.exam.springbatch_10.app.order.service;

import com.koreait.exam.springbatch_10.app.cart.entity.CartItem;
import com.koreait.exam.springbatch_10.app.cart.service.CartService;
import com.koreait.exam.springbatch_10.app.member.entity.Member;
import com.koreait.exam.springbatch_10.app.order.entity.Order;
import com.koreait.exam.springbatch_10.app.order.entity.OrderItem;
import com.koreait.exam.springbatch_10.app.order.repository.OrderRepository;
import com.koreait.exam.springbatch_10.app.product.entity.ProductOption;
import lombok.RequiredArgsConstructor;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
//클래스의 @Transactional(readOnly = true) : 스프링에서 트랜젝션을 관리해주는 것이 @Transactional
//readOnly == 읽기전용. read하는 상황에서 많이 씀. 성능때문에 쓰는 것이고 필수는 아니다.
public class OrderService {

    private final CartService cartService;
    private final OrderRepository orderRepository;


    //메서드의 @Transactional ==> 이 메서드는 DB랑 연동이 되어있으니, 여기있는 모든 작업이 끝나야 transaction commit 하도록 하는 것.
    //==> 에러가 나도 데이터에 영향을 주지 않도록.
    @Transactional
    public Order createFromCart(Member member) {
        // 전달 받은 회원의 장바구니에 있는 아이템들을 전부 가져와

        // 만약에 장바구니의 특정 상품이 판매 불가 상태야 => 삭제
        // 만약에 장바구니의 특정 상품이 판매 가능 상태야 => 주문 품목으로 옮긴 후 삭제

        //멤버에 대한 카트아이템 가져오기
        List<CartItem> cartItems = cartService.getItemsByMember(member);

        List<OrderItem> orderItems = new ArrayList<>();

        //카드아이템 순회
        for (CartItem cartItem : cartItems) {
            ProductOption productOption = cartItem.getProductOption();

            //주문 가능 여부 확인
            if (productOption.isOrderable(cartItem.getQuantity())) {
                //가능하면 주문목록에 추가
                orderItems.add(new OrderItem(productOption, cartItem.getQuantity()));
            }

            //처리 후 장바구니에서 삭제
            cartService.deleteItem(cartItem);
        }

        //빌더패턴을 통한 order 생성
        return create(member, orderItems);
    }

    @Transactional
    public Order create(Member member, List<OrderItem> orderItems) {
        Order order = Order.builder()
                .member(member)
                .build();

        //OrderItem 순회하면서 orderItem 추가
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        //최종적으로 생성된 주문을 저장
        orderRepository.save(order);

        return order;
    }
}