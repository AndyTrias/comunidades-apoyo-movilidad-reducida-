package notificaciones;

import comunidades.usuario.Usuario;

public interface medioPreferido {
  public void notificar(Usuario usuario, Notificacion notificacion);
}