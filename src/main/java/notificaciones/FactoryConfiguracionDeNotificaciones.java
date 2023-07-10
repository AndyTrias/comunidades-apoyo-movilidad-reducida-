package notificaciones;

import comunidades.usuario.configuraciones.ConfiguracionDeNotificaciones;
import comunidades.usuario.configuraciones.formas.CuandoSuceden;
import comunidades.usuario.configuraciones.formas.EstrategiaDeNotificacion;
import comunidades.usuario.configuraciones.formas.SinApuros;
import comunidades.usuario.configuraciones.medios.mail.AdapterMail;
import comunidades.usuario.configuraciones.medios.mail.NotificarPorMail;
import comunidades.usuario.configuraciones.medios.whatsapp.AdapterWhatsapp;
import comunidades.usuario.configuraciones.medios.whatsapp.NotificarPorWhatsApp;

import java.util.Date;

public class FactoryConfiguracionDeNotificaciones {
    public static ConfiguracionDeNotificaciones crearConfiguracionDeNotificaciones(String tipo) {
        ConfiguracionDeNotificaciones configuracionDeNotificaciones = new ConfiguracionDeNotificaciones();
        EstrategiaDeNotificacion estrategiaDeNotificacion = null;
        switch (tipo){
            case "W_C":
                estrategiaDeNotificacion = new CuandoSuceden();
                configuracionDeNotificaciones.setMedioDeNotificacion(new NotificarPorWhatsApp(new AdapterWhatsapp()));
                configuracionDeNotificaciones.setEstrategiaDeNotificacion(estrategiaDeNotificacion);
                break;
            case "M_C":
                estrategiaDeNotificacion = new CuandoSuceden();
                configuracionDeNotificaciones.setMedioDeNotificacion(new NotificarPorMail(new AdapterMail()));
                configuracionDeNotificaciones.setEstrategiaDeNotificacion(estrategiaDeNotificacion);
                break;

            case "W_S":
                estrategiaDeNotificacion = new SinApuros(new Date());
                configuracionDeNotificaciones.setMedioDeNotificacion(new NotificarPorWhatsApp(new AdapterWhatsapp()));
                configuracionDeNotificaciones.setEstrategiaDeNotificacion(estrategiaDeNotificacion);
                break;

            case "M_S":
                estrategiaDeNotificacion = new SinApuros(new Date());
                configuracionDeNotificaciones.setMedioDeNotificacion(new NotificarPorMail(new AdapterMail()));
                configuracionDeNotificaciones.setEstrategiaDeNotificacion(estrategiaDeNotificacion);
                break;

            default:
                throw new RuntimeException("Tipo de configuracion de notificaciones invalido");
        }

        return configuracionDeNotificaciones;
    }
}