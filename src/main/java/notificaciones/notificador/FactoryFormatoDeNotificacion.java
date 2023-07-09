package notificaciones.notificador;

import comunidades.usuario.configuraciones.EstrategiaDeNotificacion;

public class FactoryFormatoDeNotificacion {
    public static EstrategiaDeNotificacion crear(String formato) {
        EstrategiaDeNotificacion estrategiaDeNotificacion;
        switch (formato) {
            case "CUANDO_SUCEDEN": estrategiaDeNotificacion = new crearCuandoSucenden(); break;
            case "SIN_APUROS": estrategiaDeNotificacion = new crearSinApuros(); break;
            default: throw new RuntimeException("Formato de notificacion no soportado");
        }

        return estrategiaDeNotificacion;
    }
}
