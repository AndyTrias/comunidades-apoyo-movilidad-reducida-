package usuario.configuraciones.medios.mail;

import usuario.configuraciones.medios.MedioPreferido;
import lombok.Setter;
import notificaciones.Notificacion;

public class NotificarPorMail implements MedioPreferido {
    @Setter private IAdapterMail adapter;

    public NotificarPorMail(IAdapterMail adapter) {
        this.adapter = adapter;
    }

    public void notificar(Notificacion notificacion) {
        adapter.notificar(notificacion.getDestinatario().getCorreoElectronico(), notificacion.getAsunto(), notificacion.getCuerpo());
    }
}
