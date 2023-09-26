package models.usuario.configuraciones.medios.whatsapp;

import models.usuario.Usuario;
import models.notificaciones.Notificacion;

public interface IAdapterWhatsapp {
  void notificar(Usuario usuario, Notificacion notificacion);
}
