
package com.muniz.vb.orders.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String description;

    private BigDecimal price;

}
