
package com.muniz.vb.orders.service;

import com.muniz.vb.orders.domain.Order;
import com.muniz.vb.orders.domain.Product;
import com.muniz.vb.orders.dto.OrderRequest;
import com.muniz.vb.orders.dto.ProductRequest;
import com.muniz.vb.orders.exception.APIException;
import com.muniz.vb.orders.exception.BadRequestException;
import com.muniz.vb.orders.exception.ConflictException;
import com.muniz.vb.orders.exception.NotFoundException;
import com.muniz.vb.orders.repository.OrderRepository;
import com.muniz.vb.orders.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class OrderService {

  private static final String PRODUCT_NOT_EXISTS = "Product %s not exists!";

  @Autowired
  private OrderRepository repository;

  public void createOrder(final OrderRequest request) throws APIException {

  }

  public List<Order> listOrders() throws APIException {
    return repository.findAll();

  }


}
