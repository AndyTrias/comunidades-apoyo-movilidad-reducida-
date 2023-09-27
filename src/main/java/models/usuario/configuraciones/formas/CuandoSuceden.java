package models.usuario.configuraciones.formas;

import models.notificaciones.Notificacion;

public class CuandoSuceden implements EstrategiaDeNotificacion {

    public void notificar(Notificacion notificacion){
        notificacion.getDestinatario().getConfiguracionDeNotificaciones().getMedioPreferido().notificar(notificacion);
    }
}
