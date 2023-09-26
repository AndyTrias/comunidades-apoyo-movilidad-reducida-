package noServer.usuario.configuraciones.formas;

import noServer.notificaciones.Notificacion;

public class CuandoSuceden implements EstrategiaDeNotificacion {

    public void notificar(Notificacion notificacion){
        notificacion.getDestinatario().getConfiguracionDeNotificaciones().getMedioPreferido().notificar(notificacion);
    }
}
