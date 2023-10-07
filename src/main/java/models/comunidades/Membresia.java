package models.comunidades;

import models.servicios.PrestacionDeServicio;
import lombok.Getter;
import models.usuario.Usuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "membresia")
public class Membresia {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private Usuario usuario;

  @Getter
  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private Comunidad comunidad;

  @Getter
  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private Rol rol;

  @Getter
  @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "membresia_id")
  private List<Afectacion> afectaciones;

  public Membresia(Comunidad comunidad, Usuario usuario, Rol rol) {
    this.comunidad = comunidad;
    this.rol = rol;
    this.usuario = usuario;
    this.afectaciones = new ArrayList<>();
    this.afectacionesPorDefecto();
  }

  public Membresia() {}

  private void afectacionesPorDefecto() {
    for (PrestacionDeServicio prestacion : this.comunidad.getServiciosDeInteres()) {
      this.agregarAfectacion(prestacion, false);
    }
  }

  public void agregarAfectacion(PrestacionDeServicio prestacion, boolean afectado) {
    Afectacion afectacion = new Afectacion(prestacion);
    afectacion.setAfectado(afectado);
    this.afectaciones.add(afectacion);
  }

  public void cambiarAfectacion(PrestacionDeServicio prestacion, boolean b) {
    for (Afectacion afectacion : this.afectaciones) {
      if (afectacion.getPrestacionDeServicio().equals(prestacion)) {
        afectacion.setAfectado(b);
      }
    }
  }

  public Afectacion getAfectacion(PrestacionDeServicio prestacion) {
    return this.afectaciones.stream().filter(a -> a.getPrestacionDeServicio().equals(prestacion)).findFirst().get();
  }

  public boolean esAfectado() {
    return this.afectaciones.stream().anyMatch(Afectacion::isAfectado);
  }

}
