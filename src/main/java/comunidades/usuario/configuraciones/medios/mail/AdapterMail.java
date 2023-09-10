package comunidades.usuario.configuraciones.medios.mail;

import comunidades.usuario.Email;
import external.mail.ServicioMail;

public class AdapterMail implements IAdapterMail{
    private ServicioMail servicioMail = ServicioMail.getInstance();

    public void notificar(Email email, String asunto, String cuerpo) {
        servicioMail.enviarCorreo(email, asunto, cuerpo);
    }
}
