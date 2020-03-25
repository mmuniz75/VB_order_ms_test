
package com.muniz.vb.orders.repository;

import com.muniz.vb.orders.domain.Order;
import com.muniz.vb.orders.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
