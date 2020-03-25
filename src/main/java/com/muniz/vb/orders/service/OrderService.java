
package com.muniz.vb.orders.service;

import com.muniz.vb.orders.domain.Order;
import com.muniz.vb.orders.domain.OrderItem;
import com.muniz.vb.orders.domain.Product;
import com.muniz.vb.orders.dto.ItemDTO;
import com.muniz.vb.orders.dto.OrderRequest;
import com.muniz.vb.orders.dto.ProductRequest;
import com.muniz.vb.orders.exception.*;
import com.muniz.vb.orders.repository.OrderRepository;
import com.muniz.vb.orders.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class OrderService {

  private static final String PRODUCT_NOT_EXISTS = "Product %s not exists!";

  @Autowired
  private OrderRepository repository;

  @Autowired
  private ProductRepository productRepository;

  public void createOrder(final OrderRequest request) throws APIException {

    Order order = Order.builder()
                  .buyerEmail(request.getBuyerEmail())
                  .creationDate(LocalDateTime.now())
                  .build();

    List<OrderItem> items = new ArrayList<>();

    for(ItemDTO prod:request.getProducts()) {

      Product product = productRepository.findOne(prod.getProductId());
      if(Objects.isNull(product))
        throw new PreConditionException(String.format(PRODUCT_NOT_EXISTS,prod.getProductId()));

      OrderItem item = OrderItem.builder()
                       .amount(prod.getAmount())
                       .price(product.getPrice())
                       .product(product)
                       .build();

      items.add(item);
    };

    Double total = items.stream()
                   .mapToDouble(item -> item.getPrice().multiply(new BigDecimal(item.getAmount())).doubleValue())
                   .sum();

    order.setTotal(new BigDecimal(total).setScale(2,BigDecimal.ROUND_HALF_EVEN));
    order.setItems(items);

    repository.save(order);

  }

  public List<Order> listOrders(LocalDate startDate, LocalDate endDate) throws APIException {
    return repository.findByCreationDateBetween(startDate.atStartOfDay(),endDate.plusDays(1).atStartOfDay().minusSeconds(1));

  }


}
