package models.comunidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.servicios.PrestacionDeServicio;

import javax.persistence.*;

@Entity
@Table(name = "afectacion")
@NoArgsConstructor
public class Afectacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Long id;

  @Getter
  @Setter
  @Column(name = "afectado")
  private boolean afectado;

  @Getter
  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private PrestacionDeServicio prestacionDeServicio;

  public Afectacion(PrestacionDeServicio prestacionDeServicio) {
    this.prestacionDeServicio = prestacionDeServicio;
    this.afectado = true;
  }
}
