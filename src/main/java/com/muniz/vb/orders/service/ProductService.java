
package com.muniz.vb.orders.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muniz.vb.orders.domain.Product;
import com.muniz.vb.orders.dto.ProductRequest;
import com.muniz.vb.orders.exception.APIException;
import com.muniz.vb.orders.exception.BadRequestException;
import com.muniz.vb.orders.exception.ConflictException;
import com.muniz.vb.orders.exception.NotFoundException;
import com.muniz.vb.orders.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class ProductService {

  private static final String NO_FIELD_TO_UPDATE = "The current values is the same of the product";
  private static final String NO_FIELD_INFORMED = "Inform description or/and price properties";
  private static final String PRODUCT_ALREADY_EXISTS = "Product already exists!";
  private static final String PRODUCT_NOT_FOUND = "Product not found!";

  @Autowired
  private ProductRepository repository;

  public void createProduct(final Product request) throws APIException {

    if(repository.exists(request.getId()))
      throw new ConflictException(PRODUCT_ALREADY_EXISTS);

    repository.save(request);
  }

  public List<Product> listProducts() throws APIException {
    return repository.findAll();

  }

  public void updateProduct(final String id, final ProductRequest request) {

    if(!validation(request))
      throw new BadRequestException(NO_FIELD_INFORMED);

    Product product = repository.findOne(id);

    if(Objects.isNull(product))
      throw new NotFoundException(PRODUCT_NOT_FOUND);

    Product updatedProduct = Product.builder()
                             .description(request.getDescription())
                             .price(request.getPrice())
                             .build();

    repository.save(this.merge(updatedProduct,product));

  }

  private Boolean validation(ProductRequest request){

    if (Objects.isNull(request.getDescription()) && Objects.isNull(request.getPrice()) )
      return false;
    else
      return true;

  }

  private Product merge(final Product source, final Product target) {

    Product targetCopy = Product.builder().build();
    BeanUtils.copyProperties(target, targetCopy);
    BeanUtils.copyProperties(source, target, getNullPropertyNames(source));

    if (Objects.equals(target, targetCopy))
      throw new BadRequestException(NO_FIELD_TO_UPDATE);

    return target;
  }

  // Check what property is null
  private static String[] getNullPropertyNames(final Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    final java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    final Set<String> emptyNames = new HashSet<String>();
    for (final java.beans.PropertyDescriptor pd : pds) {
      final Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null)
        emptyNames.add(pd.getName());
    }
    final String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

}
