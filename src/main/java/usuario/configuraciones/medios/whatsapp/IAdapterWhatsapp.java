package usuario.configuraciones.medios.whatsapp;

import usuario.Usuario;
import notificaciones.Notificacion;

public interface IAdapterWhatsapp {
  void notificar(Usuario usuario, Notificacion notificacion);
}
