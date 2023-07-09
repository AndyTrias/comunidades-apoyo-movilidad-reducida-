package notificaciones.notificador;

import comunidades.incidentes.Incidente;
import comunidades.usuario.Usuario;
import comunidades.usuario.configuraciones.formas.FormaDeRecibir;
import notificaciones.Notificacion;

import java.util.List;

public interface Notificador {
    public void notificar(Incidente incidente);
}
