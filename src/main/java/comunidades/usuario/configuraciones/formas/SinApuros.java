package comunidades.usuario.configuraciones.formas;

import comunidades.usuario.configuraciones.formas.FormaDeRecibir;
import comunidades.usuario.configuraciones.medios.MedioPreferido;
import notificaciones.Notificacion;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class SinApuros extends FormaDeRecibir {
    private Set<Date> horarios;
    private List<Notificacion> aNotificar;

    public void notificar(Notificacion notificacion) {
        aNotificar.add(notificacion);
    }

    public void enviarNotificacionesCuandoCorresponda() {
        // cron job
    }
}
