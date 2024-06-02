package org.example.models.accounts.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

import java.util.List;

@Data
public class OrderList
implements Model {

  private List<Order> orders;
  private String nextPageTransactionTime;

  @JsonCreator
  public OrderList(
      @JsonProperty(value = "orders") List<Order> orders,
      @JsonProperty(value = "nextPageTransactionTime") String nextPageTransactionTime
  ) {
    this.orders = orders;
    this.nextPageTransactionTime = nextPageTransactionTime;
  }
}
