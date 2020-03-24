
package com.muniz.vb.orders.domain;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  @Id
  @NotEmpty
  private String id;

  @NotEmpty
  private String description;

  @NotNull
  private BigDecimal price;

}
