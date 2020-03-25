
package com.muniz.vb.orders.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Builder
@Data
public class OrderRequest {

    @Email
    @NotEmpty
    private String buyerEmail;

    @NotEmpty
    private List<ItemDTO> products;


}
