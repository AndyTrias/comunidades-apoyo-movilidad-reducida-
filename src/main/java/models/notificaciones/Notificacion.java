package models.notificaciones;

import models.converters.EstrategiaDeNotificacionConverter;
import models.usuario.Usuario;
import models.usuario.configuraciones.formas.EstrategiaDeNotificacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Notificacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "asunto")
  private String asunto;

  @Column(name = "cuerpo")
  private String cuerpo;

  @ManyToOne(cascade = CascadeType.ALL)
  private Usuario destinatario;

  @Convert(converter = EstrategiaDeNotificacionConverter.class)
  @Column(name = "estrategia_de_notificacion")
  private EstrategiaDeNotificacion estrategiaDeNotificacion;

  public Notificacion(String asunto, String cuerpo) {
    this.asunto = asunto;
    this.cuerpo = cuerpo;
  }


}
