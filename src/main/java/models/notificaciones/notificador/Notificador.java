package models.notificaciones.notificador;

import models.incidentes.Incidente;
import models.usuario.Usuario;

public interface Notificador {
    public void notificar(Usuario usuario, Incidente incidente);
}
