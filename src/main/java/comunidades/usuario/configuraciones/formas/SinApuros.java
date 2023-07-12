package comunidades.usuario.configuraciones.formas;

import notificaciones.Notificacion;

import java.util.*;

public class SinApuros implements EstrategiaDeNotificacion {
    private List<Date> horarios;
    private List<Notificacion> aNotificar;

    public SinApuros(Date horarioInicial){
        this.aNotificar = new ArrayList<>();
        this.horarios = new ArrayList<>();
        this.horarios.add(horarioInicial);
    }

    public void agregarHorario(Date horario) {
        horarios.add(horario);
    }

    public void notificar(Notificacion notificacion) {
        aNotificar.add(notificacion);
        enviarNotificacionesCuandoCorresponda();
    }

    public void enviarNotificacionesCuandoCorresponda() {
        Timer timer = new Timer();

        for (Date horario : horarios) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    for (Notificacion notificacion : aNotificar) {
                        notificacion.getDestinatario().getConfiguracionDeNotificaciones().getMedioPreferido().notificar(notificacion);
                        aNotificar.remove(notificacion);
                    }
                }
            }, horario);
        }
    }

}
