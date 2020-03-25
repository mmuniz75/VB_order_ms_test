
package com.muniz.vb.orders.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="_Order")
public class Order {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  private String buyerEmail;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime creationDate;

  private BigDecimal total;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name="order_id")
  @Fetch(value = FetchMode.JOIN)
  private List<OrderItem> items;

}
