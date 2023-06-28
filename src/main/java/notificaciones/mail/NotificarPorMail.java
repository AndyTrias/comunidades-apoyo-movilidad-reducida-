package notificaciones.mail;

import comunidades.usuario.Email;
import external.mail.ServicioMail;

public class NotificarPorMail implements IAdapterNotificarPorMail {

    public void notificar(Email email, String asunto, String cuerpo) {
        ServicioMail.enviarCorreo(email, asunto, cuerpo);
    }
}
