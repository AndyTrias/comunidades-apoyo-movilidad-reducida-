package notificaciones.notificador;

import comunidades.Comunidad;
import comunidades.incidentes.Incidente;
import comunidades.usuario.Usuario;
import notificaciones.FactoryNotificacion;
import notificaciones.Notificacion;

import java.util.ArrayList;
import java.util.List;

public class AperturaDeIncidente implements Notificador{

    public void notificar(Usuario usuarioAbridor, Incidente incidente) {
        List<Usuario> usuariosNotificados = new ArrayList<>();
        Notificacion notificacion = FactoryNotificacion.crearNotificacion("Apertura de incidente");

        for (Comunidad comunidad : usuarioAbridor.getComunidades()) {
            List<Usuario> usuariosANotificar = comunidad.getUsuarios();
            usuariosANotificar.forEach(usuario -> {
                if (!usuariosNotificados.contains(usuario)) {
                    notificacion.setDestinatario(usuario);
                    notificacion.setEstrategiaDeNotificacion(usuario.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion());
                    usuario.notificar(notificacion);
                    usuariosNotificados.add(usuario);
                }
            });
        }
    }
}
