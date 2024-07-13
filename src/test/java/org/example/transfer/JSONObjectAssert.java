package org.example.transfer;

import org.assertj.core.api.AbstractAssert;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectAssert
extends AbstractAssert<JSONObjectAssert, JSONObject> {

  protected JSONObjectAssert(JSONObject jsonObject, Class<?> selfType) {
    super(jsonObject, selfType);
  }

  public JSONObjectAssert(JSONObject jsonObject) {
    super(jsonObject, JSONObjectAssert.class);
  }

  public static JSONObjectAssert assertThat(JSONObject jsonObject) {
    return new JSONObjectAssert(jsonObject);
  }

  public <T> JSONObjectAssert hasValueIsEqualTo(String key, T value) {
    isNotNull();
    try {
      Object obj = actual.get(key);
      if (obj == null) {
        failWithMessage("Expected value: is null");
      } else {
        if (value instanceof Enum<?>) {
          if (!obj.equals(((Enum<?>) value).name())) {
            failWithMessage("Expected ENUM value: " + ((Enum<?>) value).name() + ", but was: " + obj);
          }
        } else if (value instanceof JSONObject) {
          if (!obj.toString().equals(value.toString())) {
            failWithMessage("Expected JSONObject value: " + value + ", but was: " + obj);
          }
        } else {
          if (!obj.toString().equals(value.toString())) {
            failWithMessage("Expected value: " + value + ", but was: " + obj);
          }
        }
      }
    } catch (JSONException e) {
      failWithMessage(e.getMessage());
    }
    return this;
  }

  public JSONObjectAssert hasValueIsArray(String key) {
    isNotNull();
    try {
      Object obj = actual.get(key);
      if (obj == null) {
        failWithMessage("Expected value: is null");
      } else {
        if (!(obj instanceof JSONArray)) {
          failWithMessage("Expected instance: JSONArray, but was: " + obj.getClass());
        }
      }
    } catch (JSONException e) {
      failWithMessage(e.getMessage());
    }
    return this;
  }

  public JSONObjectAssert hasValueIsObject(String key) {
    isNotNull();
    try {
      Object obj = actual.get(key);
      if (obj == null) {
        failWithMessage("Expected value: is null");
      } else {
        if (!(obj instanceof JSONObject)) {
          failWithMessage("Expected instance: JSONObject, but was: " + obj.getClass());
        }
      }
    } catch (JSONException e) {
      failWithMessage(e.getMessage());
    }
    return this;
  }
}
