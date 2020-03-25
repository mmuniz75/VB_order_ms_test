
package com.muniz.vb.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class ItemDTO {

    @NotEmpty
    @JsonProperty("id")
    private String productId;

    @NotNull
    private Integer amount;

}
