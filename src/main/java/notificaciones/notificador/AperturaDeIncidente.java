package notificaciones.notificador;

import comunidades.Comunidad;
import comunidades.incidentes.Incidente;
import comunidades.usuario.Usuario;
import notificaciones.FactoryNotificacion;
import notificaciones.Notificacion;

import java.util.List;

public class AperturaDeIncidente implements Notificador{

    public void notificar(Usuario usuarioAbridor, Incidente incidente) {
        Notificacion notificacion = FactoryNotificacion.crearNotificacion("Apertura de incidente");
        for (Comunidad comunidad : usuarioAbridor.getComunidades()) {
            List<Usuario> usuariosANotificar = comunidad.getUsuarios();
            usuariosANotificar.forEach(usuario -> {
                notificacion.setEstrategiaDeNotificacion(usuario.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion());
                usuario.notificar(notificacion);
            });
        }
    }
}
