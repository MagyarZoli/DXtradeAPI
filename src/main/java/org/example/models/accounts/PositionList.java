package org.example.models.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

import java.util.List;

@Data
public class PositionList
implements Model, AccountsModel {

  private List<Position> positions;

  @JsonCreator
  public PositionList(
      @JsonProperty(value = "positions") List<Position> positions
  ) {
    this.positions = positions;
  }

  @Override
  public List<? extends AccountModel> getAccounts() {
    return getPositions();
  }
}
