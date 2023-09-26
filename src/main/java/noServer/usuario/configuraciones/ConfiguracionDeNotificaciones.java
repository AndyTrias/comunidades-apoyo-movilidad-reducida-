package noServer.usuario.configuraciones;

import noServer.converters.EstrategiaDeNotificacionConverter;
import noServer.converters.MedioPreferidoConverter;
import noServer.usuario.configuraciones.formas.EstrategiaDeNotificacion;
import noServer.usuario.configuraciones.medios.MedioPreferido;
import lombok.Getter;
import lombok.Setter;
import noServer.notificaciones.Notificacion;

import javax.persistence.*;

@Entity
@Table(name = "configuracion_de_notificaciones")
public class ConfiguracionDeNotificaciones {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Setter
  @Convert(converter = EstrategiaDeNotificacionConverter.class)
  @Column(name = "estrategia_de_notificacion")
  private EstrategiaDeNotificacion estrategiaDeNotificacion;

  @Getter
  @Setter
  @Convert(converter = MedioPreferidoConverter.class)
  @Column(name = "medio_preferido")
  private MedioPreferido medioPreferido;

  public void notificar(Notificacion notificacion) {
    notificacion.getEstrategiaDeNotificacion().notificar(notificacion);
  }
}
