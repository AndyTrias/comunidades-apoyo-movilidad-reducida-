package models.entidades;

import lombok.NoArgsConstructor;
import models.incidentes.Incidente;
import models.localizacion.Localizacion;
import models.servicios.PrestacionDeServicio;
import lombok.Getter;
import lombok.Setter;
import models.servicios.Servicio;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "entidad")
@NoArgsConstructor
public class Entidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Getter
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "entidad_id", nullable = false)
    private Set<Establecimiento> establecimientos;

    @Setter
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Localizacion localizacion;

    @Getter
    @Setter
    @Column(name = "nombre")
    private String nombre;


    public Entidad(String nombre, Localizacion localizacion) {
        this.nombre = nombre;
        this.establecimientos = new HashSet<>();
        this.localizacion = localizacion;
    }

    public List<PrestacionDeServicio> getPrestacionesDeServicios() {
        return this.establecimientos.stream()
            .flatMap(establecimiento -> establecimiento.getServicios().stream())
            .collect(Collectors.toList());
    }

    public void agregarEstablecimiento(Establecimiento establecimiento) {
        this.establecimientos.add(establecimiento);
    }

    public void sacarEstablecimiento(Establecimiento establecimiento) {
        this.establecimientos.remove(establecimiento);
    }

    public List<Incidente> getIncidentes() {
        return this.getPrestacionesDeServicios().stream()
            .flatMap(prestacion -> prestacion.getIncidentes().stream())
            .collect(Collectors.toList());
    }
}
