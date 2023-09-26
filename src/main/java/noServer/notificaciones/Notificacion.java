package noServer.notificaciones;

import noServer.usuario.Usuario;
import noServer.usuario.configuraciones.formas.EstrategiaDeNotificacion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notificacion {
  private String asunto;
  private String cuerpo;
  private Usuario destinatario;
  private EstrategiaDeNotificacion estrategiaDeNotificacion;

  public Notificacion(String asunto, String cuerpo) {
    this.asunto = asunto;
    this.cuerpo = cuerpo;
  }


}
