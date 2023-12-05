package models.entidades;

import models.localizacion.Localizacion;
import models.servicios.PrestacionDeServicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "establecimiento")
public class Establecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @Column(name = "nombre")
    @Setter
    @Getter
    private String nombre;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Getter
    @Setter
    private Localizacion localizacion;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "establecimiento_id", nullable = false)
    @Getter private Set<PrestacionDeServicio> servicios;

    public Establecimiento(String nombre, Localizacion localizacion) {
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.servicios = new HashSet<>();
    }

    public Establecimiento() {
        this.servicios = new HashSet<>();
    }


    public void agregarServicioPrestado(PrestacionDeServicio servicio) {
        this.servicios.add(servicio);
    }
}
