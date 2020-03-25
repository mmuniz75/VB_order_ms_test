
package com.muniz.vb.orders.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

@Builder
@Data
public class OrderRequest {

    @Email
    @NotEmpty
    private String buyerEmail;

    @NotEmpty
    @Valid
    private List<ItemDTO> products;


}
