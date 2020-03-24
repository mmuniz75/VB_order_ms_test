
package com.muniz.vb.orders.repository;

import com.muniz.vb.orders.domain.Order;
import com.muniz.vb.orders.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
