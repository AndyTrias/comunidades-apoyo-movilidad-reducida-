package models.usuario.configuraciones.formas;

import models.notificaciones.Notificacion;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cuando_suceden")
public class CuandoSuceden extends EstrategiaDeNotificacion {

    public void notificar(Notificacion notificacion){
        new Thread(() -> {
            notificacion.getDestinatario().getConfiguracionDeNotificaciones().getMedioPreferido().notificar(notificacion);
        }).start();
    }
}
