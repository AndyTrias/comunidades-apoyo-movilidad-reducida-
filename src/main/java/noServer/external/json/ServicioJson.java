package noServer.external.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import noServer.rankings.informes.AdapterJson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ServicioJson implements AdapterJson {

  public String exportarAJson(List<List<String>> lista, String rutaArchivo) {

    List<Map<String, Object>> jsonList = new ArrayList<>();

    // Encabezados del Json
    List<String> headers = lista.get(0);

    // Iterar las listas
    for (int i = 1; i < lista.size(); i++) {
      List<String> fila = lista.get(i);
      Map<String, Object> jsonObject = new LinkedHashMap<>();

      // El primer encabezado es siempre una String
      jsonObject.put(headers.get(0), fila.get(0));

      // Cada lista es una lista por lo que se itera
      for (int j = 1; j < headers.size(); j++) {
        String header = headers.get(j);

        // Obtener los valores restantes de la fila y agregarlos a una lista
        List<String> values = new ArrayList<>(fila.subList(j, fila.size()));
        jsonObject.put(header, values);
      }

      jsonList.add(jsonObject);
    }

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = gson.toJson(jsonList);

    try (FileWriter fileWriter = new FileWriter(rutaArchivo)) {
      fileWriter.write(json);
    } catch (IOException e) {
      e.getStackTrace();
    }
    return rutaArchivo;
  }

}
