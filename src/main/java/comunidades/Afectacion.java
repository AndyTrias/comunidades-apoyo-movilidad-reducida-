package comunidades;

import servicios.PrestacionDeServicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "afectacion")
public class Afectacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Setter
  @Column(name = "afectado")
  private boolean afectado;

  @Getter
  @Transient
  private PrestacionDeServicio prestacionDeServicio;

  public Afectacion(PrestacionDeServicio prestacionDeServicio) {
    this.prestacionDeServicio = prestacionDeServicio;
    this.afectado = true;
  }


  public Afectacion() {

  }
}
