package noServer.notificaciones;

public class FactoryNotificacion {
    public static Notificacion crearNotificacion(String condicion) {
        return switch (condicion) {
            case "Apertura de incidente" -> new Notificacion("Incidente abierto", "El incidente ha sido abierto");
            case "Cierre de incidente" -> new Notificacion("Incidente resuelto", "El incidente ha sido resuelto");
            case "Revision de incidente" -> new Notificacion("Revisar incidente", "Revisa el incidente porfavor");
            default -> new Notificacion("Notificacion", "Notificacion");
        };
    }
}
