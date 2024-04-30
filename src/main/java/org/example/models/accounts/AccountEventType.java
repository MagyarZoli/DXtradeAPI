package org.example.models.accounts;

import org.example.models.EnumEventType;

public enum AccountEventType
implements EnumEventType {

  MARGIN_CALL,
  LIQUIDATION;
}
