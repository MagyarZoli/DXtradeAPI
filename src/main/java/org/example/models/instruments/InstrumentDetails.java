package org.example.models.instruments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

import java.util.List;

@Data
public class InstrumentDetails
implements Model, InstrumentsModel {

  private List<InstrumentDetail> instrumentDetails;

  @JsonCreator
  public InstrumentDetails(
      @JsonProperty(value = "instrumentDetails") List<InstrumentDetail> instrumentDetails
  ) {
    this.instrumentDetails = instrumentDetails;
  }

  @Override
  public List<? extends InstrumentModel> getInstruments() {
    return getInstrumentDetails();
  }
}
