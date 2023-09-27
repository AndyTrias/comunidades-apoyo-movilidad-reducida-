package models.converters;

import models.notificaciones.notificador.AperturaDeIncidente;
import models.notificaciones.notificador.CierreIncidente;
import models.notificaciones.notificador.Notificador;
import models.notificaciones.notificador.RevisionIncidente;

import javax.persistence.AttributeConverter;

public class NotificadorConverter implements AttributeConverter<Notificador, String> {
    @Override
    public String convertToDatabaseColumn(Notificador notificador) {
        return switch (notificador.getClass().getName()) {
            case "notificaciones.notificador.AperturaDeIncidente" -> "AperturaDeIncidente";
            case "notificaciones.notificador.CierreIncidente" -> "CierreIncidente";
            case "notificaciones.notificador.RevisionIncidente" -> "RevisionIncidente";
            default -> "";
        };
    }

    @Override
    public Notificador convertToEntityAttribute(String s) {
        Notificador notificador = null;

        if (s.equals("AperturaDeIncidente")) {
            notificador = new AperturaDeIncidente();
        } else if (s.equals("CierreIncidente")) {
            notificador = new CierreIncidente();
        } else if (s.equals("RevisionIncidente")) {
            notificador = new RevisionIncidente();
        }

        return notificador;
    }
}
