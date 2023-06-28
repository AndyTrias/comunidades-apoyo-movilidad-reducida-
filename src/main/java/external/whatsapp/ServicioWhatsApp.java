package external.whatsapp;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;

public class ServicioWhatsApp {
  // Find your Account Sid and Token at twilio.com/console
  public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
  public static final String AUTH_TOKEN = System.getenv("TWILIO_ACCOUNT_SID");

  public static void enviarWhatsapp(String destinatario, String mensaje) {
    final String telefono = String.format("whatsapp:%s", destinatario);


    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(new com.twilio.type.PhoneNumber(telefono),
        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
        mensaje).create();
  }
}