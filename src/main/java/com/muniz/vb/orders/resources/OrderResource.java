package com.muniz.vb.orders.resources;

import com.muniz.vb.orders.domain.Order;
import com.muniz.vb.orders.domain.Product;
import com.muniz.vb.orders.dto.OrderRequest;
import com.muniz.vb.orders.dto.ProductRequest;
import com.muniz.vb.orders.exception.APIException;
import com.muniz.vb.orders.service.OrderService;
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
@RequestMapping("/orders")
@Api(description = "REST API for Orders", tags = { "Order" })
public class OrderResource {

  @Autowired
  private OrderService service;

  @ApiOperation(value = "Create a order", httpMethod = "POST")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Order created"),
      @ApiResponse(code = 400, message = "Missing parameters in request")})
  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public void createOrder(@Valid @RequestBody(required = true) final OrderRequest request) throws APIException {
      service.createOrder(request);
  }

  @ApiOperation(value = "List orders by date", httpMethod = "GET")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "List of orders")})
  @GetMapping
  @ResponseStatus(value = HttpStatus.OK)
  public List<Order> listOrders() throws APIException {
    return service.listOrders();
  }


}
