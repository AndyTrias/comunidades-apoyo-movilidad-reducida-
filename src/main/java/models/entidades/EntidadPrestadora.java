package models.entidades;

import models.entidades.enviadorDeInformacion.AdapterEnviadorDeInformacion;
import models.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "entidad_prestadora")
public class EntidadPrestadora {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter
  @Getter
  private Long id;

  @Getter
  @Column(name = "nombre")
  private String nombre;

  @Setter
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
  @Getter
  private Usuario personaDesignada;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
  @JoinColumn(name = "entidad_prestadora_id", nullable = false)
  @Getter
  private List<Entidad> entidades;


  public EntidadPrestadora(String nombre) {
    this.nombre = nombre;
    this.entidades = new ArrayList<>();
  }

  public EntidadPrestadora() {

  }

  public void agregarEntidad(Entidad entidad) {
    entidades.add(entidad);
  }

  public void agregarEntidades(Entidad... entidades) {
    this.entidades.addAll(Arrays.asList(entidades));
  }
}
