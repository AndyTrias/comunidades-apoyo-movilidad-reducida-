package comunidades.usuario.configuraciones.medios.whatsapp;

import comunidades.usuario.Usuario;
import notificaciones.Notificacion;
import comunidades.usuario.configuraciones.medios.MedioPreferido;

public class NotificarPorWhatsApp implements MedioPreferido {

  private IAdapterWhatsapp adapter;

  public NotificarPorWhatsApp(IAdapterWhatsapp adapter) {
    this.adapter = adapter;
  }

  public void notificar(Usuario usuario, Notificacion notificacion) {
    adapter.notificar(usuario, notificacion);
  }
}


