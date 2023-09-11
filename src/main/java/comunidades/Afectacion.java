package comunidades;

import lombok.Getter;
import lombok.Setter;
import servicios.PrestacionDeServicio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Transient;

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
