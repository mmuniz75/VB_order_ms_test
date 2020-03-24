
package com.muniz.vb.orders.service;

import com.muniz.vb.orders.domain.Product;
import com.muniz.vb.orders.exception.APIException;
import com.muniz.vb.orders.exception.ConflictException;
import com.muniz.vb.orders.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Lazy
@Slf4j
public class ProductService {

  @Autowired
  private ProductRepository repository;

  public void createProduct(final Product request) throws APIException {

    if(repository.exists(request.getId()))
      throw new ConflictException("Product already exists!");

    repository.save(request);
  }

  public List<Product> listProducts() throws APIException {
    return repository.findAll();

  }

}
