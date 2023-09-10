package comunidades.usuario.configuraciones.medios.whatsapp;

import comunidades.usuario.Usuario;
import lombok.Setter;
import notificaciones.Notificacion;
import comunidades.usuario.configuraciones.medios.MedioPreferido;

public class NotificarPorWhatsApp implements MedioPreferido {

  @Setter private IAdapterWhatsapp adapter;

  public NotificarPorWhatsApp(IAdapterWhatsapp adapter) {
    this.adapter = adapter;
  }

  public void notificar(Notificacion notificacion) {
    adapter.notificar(notificacion.getDestinatario(), notificacion);
  }
}


