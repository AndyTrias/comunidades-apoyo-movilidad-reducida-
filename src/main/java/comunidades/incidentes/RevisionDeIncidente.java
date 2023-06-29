package comunidades.incidentes;

import comunidades.usuario.Usuario;

public class RevisionDeIncidente {

    public boolean estaCerca(Usuario usuario, Incidente incidente) {
        // Lógica para determinar si el usuario está cerca del incidente
        return false;
    }

    public void avisarRevisionDeIncidente(Usuario usuario, Incidente incidente) {
        // Lógica para enviar una notificación al usuario sobre la revisión del incidente
        // Utiliza el Notificador para enviar la notificación
        Notificador notificador = new Notificador();
        notificador.enviarNotificacionAComunidadesDeUsuario(incidente, usuario);
        notificador.enviarNotificacionAUsuariosInteresados(incidente);
        notificador.enviarRevisionManualDeIncidente(incidente);
    }
}
