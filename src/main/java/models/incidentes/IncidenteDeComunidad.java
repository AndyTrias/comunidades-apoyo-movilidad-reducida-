package models.incidentes;

import lombok.Getter;
import lombok.Setter;
import models.usuario.Usuario;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "incidente_de_comunidad")
public class IncidenteDeComunidad {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Long id;

  @Getter
  @Setter
  @Column(name = "estado")
  private boolean abierto;

  @Getter
  @Setter
  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private Incidente incidente;

  @Getter
  @Setter
  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
  private Usuario CerradoPor;

  @Getter
  @Setter
  @Column(name = "fecha_de_cierre")
  private Date fechaDeCierre;

  public void cerrarIncidente(Usuario usuario) {
    this.abierto = false;
    this.CerradoPor = usuario;
    this.fechaDeCierre = new Date();
  }

}