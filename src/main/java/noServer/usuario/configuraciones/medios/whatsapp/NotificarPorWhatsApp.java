package noServer.usuario.configuraciones.medios.whatsapp;

import lombok.Setter;
import noServer.notificaciones.Notificacion;
import noServer.usuario.configuraciones.medios.MedioPreferido;

public class NotificarPorWhatsApp implements MedioPreferido {

    @Setter
    private IAdapterWhatsapp adapter;

    public NotificarPorWhatsApp(IAdapterWhatsapp adapter) {
      this.adapter = adapter;
    }

    public NotificarPorWhatsApp() {
        adapter = new AdapterWhatsapp();
    }

  public void notificar(Notificacion notificacion) {
    adapter.notificar(notificacion.getDestinatario(), notificacion);
  }
}


