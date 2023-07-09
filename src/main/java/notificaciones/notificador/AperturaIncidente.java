package notificaciones.notificador;

import comunidades.usuario.Usuario;
import comunidades.usuario.configuraciones.formas.FormaDeRecibir;
import notificaciones.Notificacion;

public class AperturaIncidente implements Notificador{

    public void notificar(Notificacion notificacion, FormaDeRecibir formaDeRecibir) {
        formaDeRecibir.notificar(notificacion);
    }
}
