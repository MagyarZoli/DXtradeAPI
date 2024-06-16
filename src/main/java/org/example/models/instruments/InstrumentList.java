package org.example.models.instruments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

import java.util.List;

@Data
public class InstrumentList
implements Model, InstrumentsModel {

  private List<Instrument> instruments;

  @JsonCreator
  public InstrumentList(
      @JsonProperty(value = "instruments") List<Instrument> instruments
  ) {
    this.instruments = instruments;
  }
}
