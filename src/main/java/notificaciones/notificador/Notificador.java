package notificaciones.notificador;

import comunidades.usuario.configuraciones.formas.FormaDeRecibir;
import notificaciones.Notificacion;

public class Notificador{

    public void notificar(Notificacion notificacion, FormaDeRecibir formaDeRecibir) {
        formaDeRecibir.notificar(notificacion);
    }
}
