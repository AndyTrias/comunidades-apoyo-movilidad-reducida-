package notificaciones;

import comunidades.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notificacion {
  private String asunto;
  private String cuerpo;
  private Usuario destinatario;

  public Notificacion(String asunto, String cuerpo) {
    this.asunto = asunto;
    this.cuerpo = cuerpo;
  }


}
