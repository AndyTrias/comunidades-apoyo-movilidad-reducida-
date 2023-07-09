package comunidades.incidentes;

import comunidades.usuario.Usuario;
import lombok.Setter;
import notificaciones.Notificacion;

public class RevisionDeIncidente {
    @Setter private Notificador notificador;

    public boolean estaCerca(Usuario usuario, Incidente incidente) {
        // Lógica para determinar si el usuario está cerca del incidente
        return false;
    }

    public void avisarRevisionDeIncidente(Usuario usuario, Incidente incidente) {
        Notificacion notificacion = new Notificacion("Revisar incidente", "Revisa el incidente " + incidente.getAbiertoPor().getNombre());
        notificador.notificar(notificacion, usuario.getEstrategiaDeNotificacion().getFormaDeRecibir());
    }
}
