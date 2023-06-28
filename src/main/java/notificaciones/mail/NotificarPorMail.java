package notificaciones.mail;

import comunidades.usuario.Email;
import comunidades.usuario.Usuario;
import external.mail.ServicioMail;
import notificaciones.MedioPreferido;
import notificaciones.Notificacion;

public class NotificarPorMail implements MedioPreferido {
    private IAdapterMail adapter;

    public void notificar(Usuario usuario, Notificacion notificacion) {
        adapter.notificar(usuario.getCorreoElectronico(), notificacion.getAsunto(), notificacion.getCuerpo());
    }
}
