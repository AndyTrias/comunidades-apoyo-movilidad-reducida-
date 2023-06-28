package notificaciones.whatsapp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import comunidades.usuario.Usuario;
import external.whatsapp.ServicioWhatsApp;
import notificaciones.Notificacion;

public class AdapterWhatsapp implements IAdapterWhatsapp{

  public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
  public static final String AUTH_TOKEN = System.getenv("TWILIO_ACCOUNT_SID");


  public void notificar(Usuario usuario, Notificacion notificacion) {
    final String telefono = String.format("whatsapp:%s", usuario.getTelefono());
    final String mensaje = String.format("Hola %s, %s",  notificacion.getMensaje());

    ServicioWhatsApp.enviarWhatsapp(telefono, mensaje);
  }


  public static void enviarWhatsapp(String destinatario, String mensaje) {
      Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
      Message message = Message.creator(new com.twilio.type.PhoneNumber(telefono),
          new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
          mensaje).create();
    }

}