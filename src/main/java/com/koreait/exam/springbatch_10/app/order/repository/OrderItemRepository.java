package com.koreait.exam.springbatch_10.app.order.repository;

import com.koreait.exam.springbatch_10.app.order.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
        Page<OrderItem> findAllByIdLessThan(long id, Pageable pageable);
}
