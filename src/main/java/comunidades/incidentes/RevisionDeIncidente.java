package comunidades.incidentes;

import comunidades.usuario.Usuario;
import lombok.Setter;
import notificaciones.Notificacion;
import notificaciones.notificador.Notificador;
import notificaciones.notificador.RevisionIncidente;

import java.util.ArrayList;
import java.util.List;

public class RevisionDeIncidente {
    @Setter private Notificador notificador;

    public RevisionDeIncidente() {
        this.notificador = new RevisionIncidente();
    }

    public boolean estaCerca(Usuario usuario, Incidente incidente) {
        // Lógica para determinar si el usuario está cerca del incidente
        return false;
    }

    public void avisarRevisionDeIncidente(Usuario usuario, Incidente incidente) {
        notificador.notificar(incidente);
    }
}
