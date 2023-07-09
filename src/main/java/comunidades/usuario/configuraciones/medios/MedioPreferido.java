package comunidades.usuario.configuraciones.medios;

import comunidades.usuario.Usuario;
import notificaciones.Notificacion;

public interface MedioPreferido {
  public void notificar(Usuario usuario, Notificacion notificacion);
}
