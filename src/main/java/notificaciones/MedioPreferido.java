package notificaciones;

import comunidades.usuario.Usuario;

public interface MedioPreferido {
  public void notificar(Usuario usuario, Notificacion notificacion);
}
