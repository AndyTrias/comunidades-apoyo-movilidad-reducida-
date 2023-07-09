package comunidades.usuario.configuraciones.formas;

import notificaciones.Notificacion;

public class CuandoSuceden extends EstrategiaDeNotificacion {

    public void notificar(Notificacion notificacion){
        notificacion.getDestinatario().getConfiguracionDeNotificaciones().getMedioDeNotificacion().notificar(notificacion);
    }
}
