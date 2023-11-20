package models.usuario.configuraciones.medios;

import models.notificaciones.Notificacion;

public interface MedioPreferido {
  void notificar(Notificacion notificacion);
}
