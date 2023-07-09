package comunidades.usuario.configuraciones;

import comunidades.usuario.configuraciones.formas.FormaDeRecibir;
import comunidades.usuario.configuraciones.medios.MedioPreferido;
import lombok.Getter;
import notificaciones.Notificacion;

public class EstrategiaDeNotificacion {
  @Getter private FormaDeRecibir formaDeRecibir;
  @Getter private MedioPreferido medioDeNotificacion;

  public void notificar(Notificacion notificacion){
    formaDeRecibir.enviarNotificacion(medioDeNotificacion);
  }
}
