package notificaciones;

import usuario.configuraciones.ConfiguracionDeNotificaciones;
import usuario.configuraciones.formas.CuandoSuceden;
import usuario.configuraciones.formas.EstrategiaDeNotificacion;
import usuario.configuraciones.formas.SinApuros;
import usuario.configuraciones.medios.mail.AdapterMail;
import usuario.configuraciones.medios.mail.NotificarPorMail;
import usuario.configuraciones.medios.whatsapp.AdapterWhatsapp;
import usuario.configuraciones.medios.whatsapp.NotificarPorWhatsApp;

import java.util.Date;

public class FactoryConfiguracionDeNotificaciones {
    public static ConfiguracionDeNotificaciones crearConfiguracionDeNotificaciones(String tipo) {
        ConfiguracionDeNotificaciones configuracionDeNotificaciones = new ConfiguracionDeNotificaciones();
        EstrategiaDeNotificacion estrategiaDeNotificacion = null;
        switch (tipo){
            case "W_C":
                estrategiaDeNotificacion = new CuandoSuceden();
                configuracionDeNotificaciones.setMedioPreferido(new NotificarPorWhatsApp(new AdapterWhatsapp()));
                configuracionDeNotificaciones.setEstrategiaDeNotificacion(estrategiaDeNotificacion);
                break;
            case "M_C":
                estrategiaDeNotificacion = new CuandoSuceden();
                configuracionDeNotificaciones.setMedioPreferido(new NotificarPorMail(new AdapterMail()));
                configuracionDeNotificaciones.setEstrategiaDeNotificacion(estrategiaDeNotificacion);
                break;

            case "W_S":
                estrategiaDeNotificacion = new SinApuros(new Date());
                configuracionDeNotificaciones.setMedioPreferido(new NotificarPorWhatsApp(new AdapterWhatsapp()));
                configuracionDeNotificaciones.setEstrategiaDeNotificacion(estrategiaDeNotificacion);
                break;

            case "M_S":
                Date date = new Date();
                date.setMinutes(date.getMinutes() + 1);
                SinApuros sinApuros = new SinApuros(date);
                estrategiaDeNotificacion = sinApuros;
                configuracionDeNotificaciones.setMedioPreferido(new NotificarPorMail(new AdapterMail()));
                configuracionDeNotificaciones.setEstrategiaDeNotificacion(estrategiaDeNotificacion);
                break;

            default:
                throw new RuntimeException("Tipo de configuracion de notificaciones invalido");
        }

        return configuracionDeNotificaciones;
    }
}