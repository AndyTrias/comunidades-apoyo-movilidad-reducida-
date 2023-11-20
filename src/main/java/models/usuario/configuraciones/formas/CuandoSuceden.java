package models.usuario.configuraciones.formas;

import models.notificaciones.Notificacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("cuandoSuceden")
public class CuandoSuceden extends EstrategiaDeNotificacion {

    public void notificar(Notificacion notificacion){
        new Thread(() -> {
            notificacion.getDestinatario().getConfiguracionDeNotificaciones().getMedioPreferido().notificar(notificacion);
        }).start();
    }
}
