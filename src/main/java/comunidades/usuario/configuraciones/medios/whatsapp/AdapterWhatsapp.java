package comunidades.usuario.configuraciones.medios.whatsapp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import comunidades.usuario.Usuario;
import notificaciones.Notificacion;

public class AdapterWhatsapp implements IAdapterWhatsapp{

  public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
  public static final String AUTH_TOKEN = System.getenv("TWILIO_ACCOUNT_TOKEN");


  public void notificar(Usuario usuario, Notificacion notificacion) {
    this.enviarWhatsapp(usuario.getTelefono(), notificacion.getCuerpo());
  }


  private void enviarWhatsapp(String destinatario, String mensaje) {
      final String telefonoDestino = String.format("whatsapp:%s", destinatario);
      final String telefonoOrigen = "whatsapp:+14155238886";

      Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
          Message message = Message.creator(
                new com.twilio.type.PhoneNumber(telefonoDestino),
                new com.twilio.type.PhoneNumber(telefonoOrigen),
                mensaje
          ).create();
      }

}