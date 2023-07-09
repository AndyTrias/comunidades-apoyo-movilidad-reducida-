package notificaciones;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notificacion {
  private String mensaje;

  public Notificacion(String mensaje) {
    this.mensaje = mensaje;
  }
}
