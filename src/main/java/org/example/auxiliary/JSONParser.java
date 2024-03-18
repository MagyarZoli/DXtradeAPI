package org.example.auxiliary;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {

  public static <T> List<T> jsonObjectArrayParserToClass(JSONObject jsonArray, @NonNull String key, @NonNull Class<T> clazz) {
    try {
      List<T> result = new ArrayList<>();
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode eventsNode = objectMapper.readTree(jsonArray.toString()).path(key);
      if (eventsNode.isArray()) {
        for (JsonNode eventNode : eventsNode) {
          result.add(objectMapper.treeToValue(eventNode, clazz));
        }
      }
      return result;
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public static <T> T jsonObjectParserToClass(JSONObject jsonObject, @NonNull Class<T> clazz) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode eventsNode = objectMapper.readTree(jsonObject.toString());
      if (eventsNode.isObject()) {
        return objectMapper.treeToValue(eventsNode, clazz);
      } else {
        throw new IllegalArgumentException("jsonObject is not Object!");
      }
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public static JSONArray jsonObjectParseToJSONArray(JSONObject jsonObject, @NonNull String key) {
    return jsonObject != null ? jsonObject.getJSONArray(key) : new JSONArray();
  }
}
