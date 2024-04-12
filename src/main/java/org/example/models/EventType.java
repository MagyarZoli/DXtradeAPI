package org.example.models;

import lombok.NonNull;

public interface EventType<E extends EnumEventType> {

  E getEventType();

  void setEventType(@NonNull E eventType);
}
