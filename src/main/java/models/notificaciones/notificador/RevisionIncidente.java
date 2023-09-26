package models.notificaciones.notificador;

import models.incidentes.Incidente;
import models.usuario.Usuario;
import models.notificaciones.FactoryNotificacion;
import models.notificaciones.Notificacion;

public class RevisionIncidente implements Notificador {

    public void notificar(Usuario usuarioARevisar, Incidente incidente) {
        Notificacion notificacion = FactoryNotificacion.crearNotificacion("Revision de incidente");
        notificacion.setDestinatario(usuarioARevisar);
        notificacion.setEstrategiaDeNotificacion(usuarioARevisar.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion());
        usuarioARevisar.notificar(notificacion);
    }
}
