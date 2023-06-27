package comunidades.usuario.configNotificaciones;

import comunidades.usuario.Email;
import external.mail.ServicioMail;

public class NotificarPorMail implements AdapterNotificarPorMail{

    public void notificar(Email email, String asunto, String cuerpo) {
        ServicioMail.enviarCorreo(email, asunto, cuerpo);
    }
}
