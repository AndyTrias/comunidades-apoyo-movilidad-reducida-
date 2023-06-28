package notificaciones;

public class EstrategiaDeNotificacion {
  private FormaDeRecibir formaDeRecibir;
  private MedioPreferido medioDeNotificacion;

  public void notificar(){
    formaDeRecibir.enviarNotificacion(medioDeNotificacion);
  }
}
