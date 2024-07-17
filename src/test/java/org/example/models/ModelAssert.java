package org.example.models;

import org.assertj.core.api.AbstractAssert;
import org.example.transfer.JSONObjectAssert;
import org.json.JSONObject;

public class ModelAssert
extends AbstractAssert<ModelAssert, Model> {

  protected ModelAssert(Model model, Class<?> selfType) {
    super(model, selfType);
  }

  public ModelAssert(Model model) {
    super(model, ModelAssert.class);
  }

  public static ModelAssert assertThat(Model model) {
    return new ModelAssert(model);
  }

  public <T> ModelAssert hasValueIsEqualTo(String key, T value) {
    isNotNull();
    JSONObject jsonObject = actual.parseToJSONObject();
    JSONObjectAssert.assertThat(jsonObject).hasValueIsEqualTo(key, value);
    return this;
  }
}