package comunidades.usuario.configuraciones.formas;

import notificaciones.Notificacion;

public class CuandoSuceden extends FormaDeRecibir{

    public void notificar(Notificacion notificacion){
        notificacion.getDestinatario().getEstrategiaDeNotificacion().getMedioDeNotificacion().notificar(notificacion);
    }
}
