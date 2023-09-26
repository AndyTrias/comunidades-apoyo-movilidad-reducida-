package noServer.converters;

import noServer.usuario.configuraciones.formas.CuandoSuceden;
import noServer.usuario.configuraciones.formas.EstrategiaDeNotificacion;
import noServer.usuario.configuraciones.formas.SinApuros;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public class EstrategiaDeNotificacionConverter implements AttributeConverter<EstrategiaDeNotificacion, String> {
  @Override
  public String convertToDatabaseColumn(EstrategiaDeNotificacion estrategiaDeNotificacion) {
      return switch (estrategiaDeNotificacion.getClass().getName()) {
          case "usuario.configuraciones.formas.SinApuros" -> "SinApuros";
          case "usuario.configuraciones.formas.CuandoSuceden" -> "CuandoSuceden";
          default -> "";
      };
  }

  @Override
  public EstrategiaDeNotificacion convertToEntityAttribute(String nombre) {
    EstrategiaDeNotificacion estrategiaDeNotificacion = null;

    if (Objects.equals(nombre, "SinApuros")) {
      estrategiaDeNotificacion = new SinApuros();
    } else if (Objects.equals(nombre, "CuandoSuceden")) {
      estrategiaDeNotificacion = new CuandoSuceden();
    }

    return estrategiaDeNotificacion;
  }
}