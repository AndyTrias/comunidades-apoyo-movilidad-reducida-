package notificaciones;

public class FactoryNotificacion {
    public static Notificacion crear(String mensaje) {
        return new Notificacion(mensaje);
    }
}
