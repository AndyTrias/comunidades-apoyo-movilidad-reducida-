package noServer.notificaciones.notificador;

import noServer.incidentes.Incidente;
import noServer.usuario.Usuario;
import noServer.notificaciones.FactoryNotificacion;
import noServer.notificaciones.Notificacion;

public class RevisionIncidente implements Notificador {

    public void notificar(Usuario usuarioARevisar, Incidente incidente) {
        Notificacion notificacion = FactoryNotificacion.crearNotificacion("Revision de incidente");
        notificacion.setDestinatario(usuarioARevisar);
        notificacion.setEstrategiaDeNotificacion(usuarioARevisar.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion());
        usuarioARevisar.notificar(notificacion);
    }
}
