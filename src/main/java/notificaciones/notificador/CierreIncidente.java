package notificaciones.notificador;

import comunidades.incidentes.Incidente;
import comunidades.usuario.Usuario;
import notificaciones.FactoryNotificacion;
import notificaciones.Notificacion;

import java.util.List;

public class CierreIncidente implements Notificador {


    public void notificar(Usuario usuarioCerrador, Incidente incidente) {
        Notificacion notificacion = FactoryNotificacion.crearNotificacion("Cierre de incidente");
        usuarioCerrador.getComunidades().forEach(comunidad -> {
            List<Usuario> usuariosANotificar = comunidad.getUsuarios();
            usuariosANotificar.forEach(usuario -> {
                notificacion.setEstrategiaDeNotificacion(usuario.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion());
                usuario.notificar(notificacion);
            });
        });
    }
}
