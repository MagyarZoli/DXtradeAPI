package org.example.models.accounts.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

import java.util.List;

@Data
public class OrderResponseList
implements Model {

  private List<OrderResponse> orderResponses;

  @JsonCreator
  public OrderResponseList(
      @JsonProperty(value = "orderResponses") List<OrderResponse> orderResponses
  ) {
    this.orderResponses = orderResponses;
  }
}
