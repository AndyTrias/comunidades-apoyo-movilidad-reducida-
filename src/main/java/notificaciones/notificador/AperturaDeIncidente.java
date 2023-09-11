package notificaciones.notificador;

import comunidades.Comunidad;
import incidentes.Incidente;
import usuario.Usuario;
import entidades.Entidad;
import notificaciones.FactoryNotificacion;
import notificaciones.Notificacion;
import repositiorios.RepoEntidades;

import java.util.ArrayList;
import java.util.List;

public class AperturaDeIncidente implements Notificador{

    public void notificar(Usuario usuarioAbridor, Incidente incidente) {
        List<Usuario> usuariosNotificados = new ArrayList<>();
        Notificacion notificacion = FactoryNotificacion.crearNotificacion("Apertura de incidente");

        for (Comunidad comunidad : usuarioAbridor.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(incidente.getPrestacionDeServicio())).toList()) {
            List<Usuario> usuariosANotificar = comunidad.getUsuarios();
            usuariosANotificar.forEach(usuario -> {
                if (!usuariosNotificados.contains(usuario)) {
                    this.notificarAUsuario(usuario, notificacion);
                    usuariosNotificados.add(usuario);
                }
            });
        }

        /*// Notificar a interesados
        List<Entidad> entidadesConLaPrestacion = RepoEntidades.getInstance().getEntidadesConPrestacion(incidente.getPrestacionDeServicio());
        List<Usuario> usuariosConInteresEnElServicio = RepoUsuarios.getInstance().getUsuariosConInteresEnServicio(incidente.getPrestacionDeServicio().getServicio());

        // filtro a los usuarios que no tengan alguna de las entidades relacionadas en su interes
        List<Usuario> usuariosANotificarPorInteres = usuariosConInteresEnElServicio.stream().filter(usuario -> usuario.getInteres().getEntidades().stream().anyMatch(entidadesConLaPrestacion::contains)).toList();

        usuariosANotificarPorInteres.forEach(usuario -> {
            if (!usuariosNotificados.contains(usuario)) {
                this.notificarAUsuario(usuario, notificacion);
                usuariosNotificados.add(usuario);
            }
    }*/
    }

    private void notificarAUsuario(Usuario usuario, Notificacion notificacion) {
        notificacion.setDestinatario(usuario);
        notificacion.setEstrategiaDeNotificacion(usuario.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion());
        usuario.notificar(notificacion);
    }
}
