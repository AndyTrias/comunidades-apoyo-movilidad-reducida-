package notificaciones.notificador;

import comunidades.incidentes.Incidente;
import comunidades.usuario.Usuario;

public interface Notificador {
    public void notificar(Usuario usuario, Incidente incidente);
}
