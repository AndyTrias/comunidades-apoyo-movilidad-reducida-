package noServer.usuario.configuraciones.medios.mail;

import noServer.usuario.configuraciones.medios.MedioPreferido;
import lombok.Setter;
import noServer.notificaciones.Notificacion;

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