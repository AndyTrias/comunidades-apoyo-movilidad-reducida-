package models.usuario.configuraciones.medios.mail;

import models.usuario.configuraciones.medios.MedioPreferido;
import lombok.Setter;
import models.notificaciones.Notificacion;

public class NotificarPorMail implements MedioPreferido {
    @Setter
    private IAdapterMail adapter;

    public NotificarPorMail(IAdapterMail adapter) {
        this.adapter = adapter;
    }

    public NotificarPorMail() {
        adapter = new AdapterMail();
    }

    public void notificar(Notificacion notificacion) {
        adapter.notificar(notificacion.getDestinatario().getCorreoElectronico(), notificacion.getAsunto(), notificacion.getCuerpo());
    }
}
