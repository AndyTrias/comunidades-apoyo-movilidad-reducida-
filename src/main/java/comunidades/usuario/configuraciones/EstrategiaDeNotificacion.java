package comunidades.usuario.configuraciones;

import comunidades.usuario.configuraciones.formas.FormaDeRecibir;
import comunidades.usuario.configuraciones.medios.MedioPreferido;
import lombok.Getter;
import lombok.Setter;
import notificaciones.Notificacion;

public class EstrategiaDeNotificacion {
  @Getter @Setter private FormaDeRecibir formaDeRecibir;
  @Getter @Setter private MedioPreferido medioDeNotificacion;
}
