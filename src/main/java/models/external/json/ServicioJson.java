package models.external.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import models.configs.Config;
import models.entidades.Entidad;
import models.rankings.estrategiaDeExportacion.AdapterJson;
import models.rankings.informes.Ranking;
import models.repositorios.RepoEntidad;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class ServicioJson implements AdapterJson {

  @Override
  public void exportarAJson(List<Ranking> informe, String nombreArchivo) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    SimpleModule module = new SimpleModule();
    module.addSerializer(Ranking.class, new RankingJsonSerializer());
    module.addDeserializer(Ranking.class, new RankingJsonDeserializer());
    mapper.registerModule(module);
    try {
      mapper.writeValue(new File(nombreArchivo), informe);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<Ranking> importarDesdeJson(String nombreArchivo) {
    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Ranking.class, new RankingJsonDeserializer());
    mapper.registerModule(module);
    try {
      return mapper.readValue(new File(nombreArchivo), new TypeReference<>() {});
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static class RankingJsonSerializer extends JsonSerializer<Ranking> {
    @Override
    public void serialize(Ranking value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      gen.writeStartObject();
      gen.writeNumberField("entidad", value.entidad().getId());
      gen.writeNumberField("valor", value.valor());
      gen.writeEndObject();
    }
  }

  private static class RankingJsonDeserializer extends StdDeserializer<Ranking> {
    public RankingJsonDeserializer() {
      super(Ranking.class);
    }

    @Override
    public Ranking deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
      JsonNode node = p.getCodec().readTree(p);
      long entityId = node.get("entidad").asLong();
      int valor = node.get("valor").asInt();
      Entidad entidad = RepoEntidad.INSTANCE.buscar(entityId);

      return new Ranking(entidad, valor);
    }
  }

}