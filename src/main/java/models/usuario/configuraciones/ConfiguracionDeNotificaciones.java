package models.usuario.configuraciones;

import models.converters.EstrategiaDeNotificacionConverter;
import models.converters.MedioPreferidoConverter;
import models.usuario.configuraciones.formas.EstrategiaDeNotificacion;
import models.usuario.configuraciones.medios.MedioPreferido;
import lombok.Getter;
import lombok.Setter;
import models.notificaciones.Notificacion;

import javax.persistence.*;

@Entity
@Table(name = "configuracion_de_notificaciones")
public class ConfiguracionDeNotificaciones {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Setter
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
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
