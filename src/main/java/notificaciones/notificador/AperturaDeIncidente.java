package notificaciones.notificador;

import comunidades.Comunidad;
import comunidades.incidentes.Incidente;
import comunidades.usuario.Usuario;
import comunidades.usuario.configuraciones.formas.FormaDeRecibir;
import notificaciones.FactoryNotificacion;
import notificaciones.Notificacion;

import java.util.List;

public class AperturaDeIncidente implements Notificador{

    public void notificar(Incidente incidente) {
        Notificacion notificacion = FactoryNotificacion.crearNotificacion("Apertura de incidente");
        for (Comunidad comunidad : incidente.getAbiertoPor().getComunidades()) {
            List<Usuario> usuariosANotificar = comunidad.getUsuarios();
            usuariosANotificar.forEach(usuario -> {
                notificacion.setFormaDeRecibir(usuario.getEstrategiaDeNotificacion().getFormaDeRecibir());
                usuario.notificar(notificacion);
            });
        }
    }
}
