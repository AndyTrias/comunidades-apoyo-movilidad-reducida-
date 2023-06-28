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

  public static void main(String[] args) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
        new com.twilio.type.PhoneNumber("whatsapp:+5491138791319"),
        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
        "Your Yummy Cupcakes Company order of 1 dozen frosted cupcakes has shipped and should be delivered on July 10, 2019. Details: http://www.yummycupcakes.com/"

    ).create();

    System.out.println(message.getSid());
  }
}