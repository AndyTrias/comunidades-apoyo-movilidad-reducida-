package notificaciones.formaDeRecibir;

import notificaciones.FormaDeRecibir;
import notificaciones.MedioPreferido;
import notificaciones.Notificacion;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class SinApuros implements FormaDeRecibir {
    private Set<Date> horarios;
    private List<Notificacion> aNotificar;

    public void notificar(){

    }

    public void enviarNotificacion(MedioPreferido medioDeNotificacion) {
        medioDeNotificacion.notificar(null, null);
    }
}
