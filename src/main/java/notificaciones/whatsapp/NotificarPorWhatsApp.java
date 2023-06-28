package notificaciones.whatsapp;

import comunidades.usuario.Usuario;
import notificaciones.Notificacion;
import notificaciones.medioPreferido;

public class NotificarPorWhatsApp implements medioPreferido {

  private IAdapterWhatsapp adapter;

  public NotificarPorWhatsApp(IAdapterWhatsapp adapter) {
    this.adapter = adapter;
  }

  public void notificar(Usuario usuario, Notificacion notificacion) {
    adapter.notificar(usuario, notificacion);
  }
}


