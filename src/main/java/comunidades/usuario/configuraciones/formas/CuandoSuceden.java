package comunidades.usuario.configuraciones.formas;

import notificaciones.Notificacion;

public class CuandoSuceden extends FormaDeRecibir{
    public void notificar(Notificacion notificacion){
        medioPreferido.notificar(notificacion);
    }
}
