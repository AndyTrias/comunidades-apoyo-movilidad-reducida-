package comunidades.usuario.configuraciones.formas;

import notificaciones.Notificacion;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class SinApuros extends EstrategiaDeNotificacion {
    private Set<Date> horarios;
    private List<Notificacion> aNotificar;

    public void notificar(Notificacion notificacion) {
        aNotificar.add(notificacion);
    }

    public void enviarNotificacionesCuandoCorresponda() {
        // cron job
    }
}
