package comunidades.usuario.configuraciones.formas;

import notificaciones.Notificacion;

import java.util.*;

public class SinApuros extends EstrategiaDeNotificacion {
    private Set<Date> horarios;
    private List<Notificacion> aNotificar;

    public SinApuros(Date horarioInicial){
        this.aNotificar = new ArrayList<>();
        this.horarios = new HashSet<>();
        this.horarios.add(horarioInicial);
    }

    public void agregarHorario(Date horario) {
        horarios.add(horario);
    }

    public void notificar(Notificacion notificacion) {
        aNotificar.add(notificacion);
    }

    public void enviarNotificacionesCuandoCorresponda() {
        // cron job
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Notificacion notificacion : aNotificar) {
                    notificacion.getDestinatario().getConfiguracionDeNotificaciones().getMedioDeNotificacion().notificar(notificacion);
                }
            }
        }, (Date) horarios);

    }
}
