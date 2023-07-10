package notificaciones.notificador;

import comunidades.incidentes.Incidente;
import comunidades.usuario.Usuario;
import notificaciones.FactoryNotificacion;
import notificaciones.Notificacion;

public class RevisionIncidente implements Notificador {

    public void notificar(Usuario usuarioARevisar, Incidente incidente) {
        Notificacion notificacion = FactoryNotificacion.crearNotificacion("Revision de incidente");
        notificacion.setDestinatario(usuarioARevisar);
        notificacion.setEstrategiaDeNotificacion(usuarioARevisar.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion());
        usuarioARevisar.notificar(notificacion);
    }
}
