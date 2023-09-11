package notificaciones.notificador;

import incidentes.Incidente;
import usuario.Usuario;
import notificaciones.FactoryNotificacion;
import notificaciones.Notificacion;

import java.util.ArrayList;
import java.util.List;

public class CierreIncidente implements Notificador {


    public void notificar(Usuario usuarioCerrador, Incidente incidente) {
        List<Usuario> usuariosNotificados = new ArrayList<>();
        Notificacion notificacion = FactoryNotificacion.crearNotificacion("Cierre de incidente");
        usuarioCerrador.getComunidades().forEach(comunidad -> comunidad.getUsuarios().forEach(usuario -> {
            if (!usuariosNotificados.contains(usuario)) {
                notificacion.setDestinatario(usuario);
                notificacion.setEstrategiaDeNotificacion(usuario.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion());
                usuario.notificar(notificacion);
                usuariosNotificados.add(usuario);
            }
        }));

        /*List<Entidad> entidadesConLaPrestacion = RepoEntidades.getInstance().getEntidadesConPrestacion(incidente.getPrestacionDeServicio());
        List<Usuario> usuariosConInteresEnElServicio = RepoUsuarios.getInstance().getUsuariosConInteresEnServicio(incidente.getPrestacionDeServicio().getServicio());

        // filtro a los usuarios que no tengan alguna de las entidades relacionadas en su interes
        List<Usuario> usuariosANotificarPorInteres = usuariosConInteresEnElServicio.stream().filter(usuario -> usuario.getInteres().getEntidades().stream().anyMatch(entidadesConLaPrestacion::contains)).toList();

        usuariosANotificarPorInteres.forEach(usuario -> {
            if (!usuariosNotificados.contains(usuario)) {
                notificacion.setDestinatario(usuario);
                notificacion.setEstrategiaDeNotificacion(usuario.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion());
                usuario.notificar(notificacion);
                usuariosNotificados.add(usuario);
            }
        });*/
    }
}
