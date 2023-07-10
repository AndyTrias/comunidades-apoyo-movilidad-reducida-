package comunidades;

import comunidades.servicios.PrestacionDeServicio;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Membresia {
  @Getter private Comunidad comunidad;
  @Getter private Rol rol;
  @Getter private List<Afectacion> afectaciones;



  public Membresia(Comunidad comunidad, Rol rol) {
    this.comunidad = comunidad;
    this.rol = rol;
    this.afectaciones = new ArrayList<>();
    this.afectacionesPorDefecto();
  }

  private void afectacionesPorDefecto() {
    for (PrestacionDeServicio prestacion : this.comunidad.getServiciosDeInteres()) {
      this.agregarAfectacion(prestacion, true);
    }
  }

  public void agregarAfectacion(PrestacionDeServicio prestacion, boolean afectado) {
    Afectacion afectacion = new Afectacion(prestacion);
    afectacion.setAfectado(afectado);
    this.afectaciones.add(afectacion);
  }

  public void cambiarAfectacion(PrestacionDeServicio prestacion, boolean b){
    for (Afectacion afectacion : this.afectaciones) {
      if (afectacion.getPrestacionDeServicio().equals(prestacion)) {
        afectacion.setAfectado(b);
      }
    }
  }

  public Afectacion getAfectacion(PrestacionDeServicio prestacion) {
    return this.afectaciones.stream().filter(a -> a.getPrestacionDeServicio().equals(prestacion)).findFirst().get();
  }

  public boolean esAfectado(){
    return this.afectaciones.stream().anyMatch(Afectacion::isAfectado);
  }

}
