package models.rankings.informes;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomDeserializer implements JsonDeserializer<Map<String, List<Long>>> {
  @Override
  public Map<String, List<Long>> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    Map<String, List<Long>> result = new HashMap<>();
    JsonArray jsonArray = json.getAsJsonArray();
    for (JsonElement element : jsonArray) {
      JsonObject jsonObject = element.getAsJsonObject();
      String criterio = jsonObject.get("Criterio").getAsString();
      String rankingString = jsonObject.get("Ranking").getAsString();
      String[] rankingArray = rankingString.split(", ");
      List<Long> ranking = Arrays.stream(rankingArray).map(Long::valueOf).collect(Collectors.toList());
      result.put(criterio, ranking);
    }
    return result;
  }
}
