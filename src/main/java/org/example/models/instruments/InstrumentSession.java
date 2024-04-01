package org.example.models.instruments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InstrumentSession {

  /**
   * UTC week date the event occurs (e.g. Monday, 23:00:00Z).
   */
  private String weekDay;

  /**
   * Either SESSION_OPEN or SESSION_CLOSE.
   */
  private SessionEventType eventType;

  @JsonCreator
  public InstrumentSession(
      @JsonProperty(value = "weekDay") String weekDay,
      @JsonProperty(value = "eventType") SessionEventType eventType
  ) {
    this.weekDay = weekDay;
    this.eventType = eventType;
  }
}
