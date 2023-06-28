package notificaciones.whatsapp;

import comunidades.usuario.Usuario;
import external.whatsapp.ServicioWhatsApp;
import notificaciones.Notificacion;

public class AdapterWhatsapp implements IAdapterWhatsapp{

  private ServicioWhatsApp adaptadaAWhatsapp;

  public AdapterWhatsapp(ServicioWhatsApp adaptadaAWhatsapp) {
    this.adaptadaAWhatsapp = adaptadaAWhatsapp;
  }

  public void notificar(Usuario usuario, Notificacion notificacion) {
    adaptadaAWhatsapp.enviarWhatsapp(usuario.getTelefono(), notificacion.getMensaje());
  }
}
