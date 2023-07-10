package comunidades.usuario.configuraciones.formas;

import notificaciones.Notificacion;

public class CuandoSuceden implements EstrategiaDeNotificacion {

    public void notificar(Notificacion notificacion){
        notificacion.getDestinatario().getConfiguracionDeNotificaciones().getMedioPreferido().notificar(notificacion);
    }
}
