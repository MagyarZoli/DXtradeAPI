package org.example.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.APIException;
import org.json.JSONObject;

public interface Model {

  default JSONObject parseToJSONObject() {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return new JSONObject(objectMapper.writeValueAsString(this));
    } catch (JsonProcessingException e) {
      throw new APIException(e.getMessage(), e);
    }
  }
}
