package models.notificaciones.notificador;

import models.incidentes.Incidente;
import models.notificaciones.FactoryNotificacion;
import models.notificaciones.Notificacion;
import models.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class CierreIncidente implements Notificador {


    public void notificar(Usuario usuarioCerrador, Incidente incidente) {
        List<Usuario> usuariosNotificados = new ArrayList<>();
        Notificacion notificacion = FactoryNotificacion.crearNotificacion("Cierre de incidente");
        usuarioCerrador.getComunidades().forEach(comunidad -> comunidad.getMembresias().forEach(m -> {
            if (!usuariosNotificados.contains(m.getUsuario())) {
                notificacion.setDestinatario(m.getUsuario());
                notificacion.setEstrategiaDeNotificacion(m.getUsuario().getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion());
                m.getUsuario().notificar(notificacion);
                usuariosNotificados.add(m.getUsuario());
            }
        }));

    }
}
