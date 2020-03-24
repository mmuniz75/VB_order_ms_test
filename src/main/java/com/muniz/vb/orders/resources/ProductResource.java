package com.muniz.vb.orders.resources;

import com.muniz.vb.orders.domain.Product;
import com.muniz.vb.orders.dto.ProductRequest;
import com.muniz.vb.orders.exception.APIException;
import com.muniz.vb.orders.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
@Api(description = "REST API for Products", tags = { "Product" })
public class ProductResource {

  @Autowired
  private ProductService service;

  @ApiOperation(value = "Create a product", httpMethod = "POST")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Product created"),
      @ApiResponse(code = 409, message = "Product already exists"),
      @ApiResponse(code = 400, message = "Missing parameters in request")})
  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public void createProduct(@Valid @RequestBody(required = true) final Product request) throws APIException {
      service.createProduct(request);
  }

  @ApiOperation(value = "List the products", httpMethod = "GET")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "List of products")})
  @GetMapping
  @ResponseStatus(value = HttpStatus.OK)
  public List<Product> listProducts() throws APIException {
    return service.listProducts();
  }

  @ApiOperation(value = "Update product's fields", httpMethod = "PATCH")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Product field updated"),
          @ApiResponse(code = 404, message = "Product not found"),
          @ApiResponse(code = 400, message = "Missing parameters in request")})
  @PatchMapping("/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public void updateProduct(@PathVariable final String id,
                            @RequestBody(required = true) final ProductRequest request) throws APIException {
    service.updateProduct(id,request);
  }

}
