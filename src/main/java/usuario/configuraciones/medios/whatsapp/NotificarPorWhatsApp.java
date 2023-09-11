package usuario.configuraciones.medios.whatsapp;

import lombok.Setter;
import notificaciones.Notificacion;
import usuario.configuraciones.medios.MedioPreferido;

public class NotificarPorWhatsApp implements MedioPreferido {

  @Setter private IAdapterWhatsapp adapter;

  public NotificarPorWhatsApp(IAdapterWhatsapp adapter) {
    this.adapter = adapter;
  }

    public NotificarPorWhatsApp() {}

  public void notificar(Notificacion notificacion) {
    adapter.notificar(notificacion.getDestinatario(), notificacion);
  }
}


