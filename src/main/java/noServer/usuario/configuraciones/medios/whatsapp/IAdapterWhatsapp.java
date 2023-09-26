package noServer.usuario.configuraciones.medios.whatsapp;

import noServer.usuario.Usuario;
import noServer.notificaciones.Notificacion;

public interface IAdapterWhatsapp {
  void notificar(Usuario usuario, Notificacion notificacion);
}
