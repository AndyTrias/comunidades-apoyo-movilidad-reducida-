package incidentes;

import comunidades.usuario.Usuario;
import lombok.Setter;
import notificaciones.notificador.Notificador;
import notificaciones.notificador.RevisionIncidente;

public class RevisionDeIncidente {
    @Setter private Notificador notificador;

    public RevisionDeIncidente() {
        this.notificador = new RevisionIncidente();
    }

    public boolean estaCerca(Usuario usuario, Incidente incidente) {
        return false;
    }

    public void avisarRevisionDeIncidente(Usuario usuario, Incidente incidente) {
        notificador.notificar(usuario, incidente);
    }
}
