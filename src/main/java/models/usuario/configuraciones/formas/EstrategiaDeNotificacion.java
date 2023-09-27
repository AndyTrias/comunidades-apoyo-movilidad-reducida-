package models.usuario.configuraciones.formas;

import models.notificaciones.Notificacion;

public interface EstrategiaDeNotificacion {
    void notificar(Notificacion notificacion);
}
