package usuario.configuraciones;

import usuario.configuraciones.formas.EstrategiaDeNotificacion;
import usuario.configuraciones.medios.MedioPreferido;
import lombok.Getter;
import lombok.Setter;
import notificaciones.Notificacion;

public class ConfiguracionDeNotificaciones {
  @Getter @Setter private EstrategiaDeNotificacion estrategiaDeNotificacion;
  @Getter @Setter private MedioPreferido medioPreferido;

  public void notificar(Notificacion notificacion) {
    notificacion.getEstrategiaDeNotificacion().notificar(notificacion);
  }
}
