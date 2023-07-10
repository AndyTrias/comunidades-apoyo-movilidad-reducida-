package comunidades;

import comunidades.servicios.PrestacionDeServicio;
import lombok.Getter;
import lombok.Setter;

public class Afectacion {
  @Getter @Setter private boolean afectado;
  @Getter private PrestacionDeServicio prestacionDeServicio;

  public Afectacion(PrestacionDeServicio prestacionDeServicio) {
    this.prestacionDeServicio = prestacionDeServicio;
    this.afectado = true;
  }


}
