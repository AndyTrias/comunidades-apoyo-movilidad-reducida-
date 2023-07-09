package notificaciones.notificador;

import comunidades.usuario.Usuario;
import comunidades.usuario.configuraciones.formas.FormaDeRecibir;
import notificaciones.Notificacion;

public interface Notificador {
    void notificar(Notificacion notificacion, FormaDeRecibir formaDeRecibir);
}
