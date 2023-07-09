package notificaciones;

import comunidades.usuario.Usuario;
import comunidades.usuario.configuraciones.formas.FormaDeRecibir;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notificacion {
  private String asunto;
  private String cuerpo;
  private Usuario destinatario;
  private FormaDeRecibir formaDeRecibir;

  public Notificacion(String asunto, String cuerpo) {
    this.asunto = asunto;
    this.cuerpo = cuerpo;
  }


}
