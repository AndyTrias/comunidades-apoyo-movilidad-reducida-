package notificaciones.notificador;

import comunidades.usuario.Usuario;
import comunidades.usuario.configuraciones.formas.FormaDeRecibir;
import notificaciones.FactoryNotificacion;
import notificaciones.Notificacion;

import java.util.List;

public class AperturaDeIncidente implements Notificador{

    public void notificar(List<Usuario> usuarios) {
        Notificacion notificacion = FactoryNotificacion.crearNotificacion("Apertura de incidente");
        for (Usuario usuario : usuarios) {
            notificacion.setFormaDeRecibir(usuario.getEstrategiaDeNotificacion().getFormaDeRecibir());
            usuario.notificar(notificacion);
        }
    }
}
