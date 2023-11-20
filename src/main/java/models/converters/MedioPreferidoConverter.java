package models.converters;

import models.usuario.configuraciones.medios.MedioPreferido;
import models.usuario.configuraciones.medios.mail.NotificarPorMail;
import models.usuario.configuraciones.medios.whatsapp.NotificarPorWhatsApp;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public class MedioPreferidoConverter implements AttributeConverter<MedioPreferido, String> {
    @Override
    public String convertToDatabaseColumn(MedioPreferido medioPreferido) {
        return switch (medioPreferido.getClass().getName()) {
            case "models.usuario.configuraciones.medios.mail.NotificarPorMail" -> "NotificarPorMail";
            case "models.usuario.configuraciones.medios.mail.NotificarPorWhatsApp" -> "NotificarPorWhatsApp";
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
