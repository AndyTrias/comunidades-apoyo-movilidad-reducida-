package noServer.notificaciones.notificador;

import noServer.incidentes.Incidente;
import noServer.usuario.Usuario;

public interface Notificador {
    public void notificar(Usuario usuario, Incidente incidente);
}
