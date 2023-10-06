package models.notificaciones.notificador;

import models.comunidades.Comunidad;
import models.comunidades.Membresia;
import models.incidentes.Incidente;
import models.usuario.Usuario;
import models.notificaciones.FactoryNotificacion;
import models.notificaciones.Notificacion;

import java.util.ArrayList;
import java.util.List;

public class AperturaDeIncidente implements Notificador{

    public void notificar(Usuario usuarioAbridor, Incidente incidente) {
        List<Usuario> usuariosNotificados = new ArrayList<>();
        Notificacion notificacion = FactoryNotificacion.crearNotificacion("Apertura de incidente");

        for (Comunidad comunidad : usuarioAbridor.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(incidente.getPrestacionDeServicio())).toList()) {
            List<Membresia> membresias= comunidad.getMembresias();
            membresias.forEach(m -> {
                if (!usuariosNotificados.contains(m.getUsuario())) {
                    this.notificarAUsuario(m.getUsuario(), notificacion);
                    usuariosNotificados.add(m.getUsuario());
                }
            });
        }
    }

    private void notificarAUsuario(Usuario usuario, Notificacion notificacion) {
        notificacion.setDestinatario(usuario);
        notificacion.setEstrategiaDeNotificacion(usuario.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion());
        usuario.notificar(notificacion);
    }
}
