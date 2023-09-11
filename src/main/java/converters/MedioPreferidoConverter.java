package converters;

import usuario.configuraciones.ConfiguracionDeNotificaciones;
import usuario.configuraciones.formas.CuandoSuceden;
import usuario.configuraciones.formas.EstrategiaDeNotificacion;
import usuario.configuraciones.formas.SinApuros;
import usuario.configuraciones.medios.MedioPreferido;
import usuario.configuraciones.medios.mail.NotificarPorMail;
import usuario.configuraciones.medios.whatsapp.NotificarPorWhatsApp;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public class MedioPreferidoConverter implements AttributeConverter<MedioPreferido, String> {
    @Override
    public String convertToDatabaseColumn(MedioPreferido medioPreferido) {
        System.out.println(medioPreferido.getClass().getName());
        return switch (medioPreferido.getClass().getName()) {
            case "usuario.configuraciones.medios.mail.NotificarPorMail" -> "NotificarPorMail";
            case "usuario.configuraciones.medios.mail.NotificarPorWhatsApp" -> "NotificarPorWhatsApp";
            default -> "";
        };
    }

    @Override
    public MedioPreferido convertToEntityAttribute(String nombre) {
        MedioPreferido medioPreferido = null;

        if (Objects.equals(nombre, "NotificarPorMail")) {
            medioPreferido = new NotificarPorMail();
        } else if (Objects.equals(nombre, "NotificarPorWhatsApp")) {
            medioPreferido = new NotificarPorWhatsApp();
        }

        return medioPreferido;
    }
}
