package comunidades.usuario.configuraciones;

import comunidades.usuario.configuraciones.formas.EstrategiaDeNotificacion;
import comunidades.usuario.configuraciones.medios.MedioPreferido;
import lombok.Getter;
import lombok.Setter;
import notificaciones.Notificacion;

public class ConfiguracionDeNotificaciones {
  @Getter @Setter private EstrategiaDeNotificacion estrategiaDeNotificacion;
  @Getter @Setter private MedioPreferido medioDeNotificacion;

  public void notificar(Notificacion notificacion) {
    estrategiaDeNotificacion.notificar(notificacion);
  }
}
