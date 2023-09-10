package usuario.configuraciones.medios.mail;

import external.mail.ServicioMail;

public class AdapterMail implements IAdapterMail{
    private ServicioMail servicioMail = ServicioMail.getInstance();

    public void notificar(String email, String asunto, String cuerpo) {
        servicioMail.enviarCorreo(email, asunto, cuerpo);
    }
}
