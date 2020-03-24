package com.muniz.vb.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = {"com.muniz.vb.orders"})
public class OrdersApplication {

  public static void main(final String[] args) {

    SpringApplication.run(OrdersApplication.class, args);
  }

}
