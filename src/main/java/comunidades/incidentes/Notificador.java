package comunidades.incidentes;

import comunidades.usuario.Usuario;

public class Notificador {

    public void enviarNotificacionAUsuariosInteresados(Incidente incidente) {
        // Lógica para enviar notificaciones a los usuarios interesados en el incidente
        // Puede implicar enviar notificaciones por correo electrónico, mensajes de texto, etc.
        //System.out.println("Enviando notificaciones a los usuarios interesados en el incidente: " + incidente.getDescripcion());
    }

    public void enviarNotificacionAComunidadesDeUsuario(Incidente incidente, Usuario usuario) {
        // Lógica para enviar notificaciones a las comunidades a las que pertenece un usuario específico
        // Puede implicar enviar notificaciones por correo electrónico, mensajes de texto, etc.
        //System.out.println("Enviando notificaciones a las comunidades del usuario " + usuario.getNombre() + " sobre el incidente: " + incidente.getDescripcion());
    }

    public void enviarRevisionManualDeIncidente(Incidente incidente) {
        // Lógica para enviar una notificación solicitando la revisión manual de un incidente
        // Esto puede implicar enviar una notificación a un equipo de revisores o a un usuario con privilegios de revisión
        //System.out.println("Enviando solicitud de revisión manual del incidente: " + incidente.getDescripcion());
    }

}
