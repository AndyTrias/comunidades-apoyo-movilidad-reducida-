package comunidades.usuario.configuraciones.formas;

import notificaciones.Notificacion;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class SinApuros extends EstrategiaDeNotificacion {
    private Set<Date> horarios;
    private List<Notificacion> aNotificar;

    public void notificar(Notificacion notificacion) {
        aNotificar.add(notificacion);
    }

    public void enviarNotificacionesCuandoCorresponda() {
        // cron job
        Timer timer = new Timer();
        timer.schedule((TimerTask) new TimerTask() {
            @Override
            public void run() {
                for (Notificacion notificacion : aNotificar) {
                    notificacion.getDestinatario().getConfiguracionDeNotificaciones().getMedioDeNotificacion().notificar(notificacion);
                }
            }
        }, (Date) horarios);

    }
}
