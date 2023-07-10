package comunidades.usuario.configuraciones.medios.whatsapp;

import comunidades.usuario.Usuario;
import notificaciones.Notificacion;

public interface IAdapterWhatsapp {
  void notificar(Usuario usuario, Notificacion notificacion);
}
