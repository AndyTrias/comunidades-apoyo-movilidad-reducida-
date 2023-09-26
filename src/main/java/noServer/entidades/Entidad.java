package noServer.entidades;

import noServer.localizacion.Localizacion;
import noServer.servicios.PrestacionDeServicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "entidad")
public class Entidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "entidad_id")
    private Set<Establecimiento> establecimientos;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private Localizacion localizacion;

    @Getter
    @Column(name = "nombre")
    private String nombre;


    public Entidad(String nombre, Localizacion localizacion) {
        this.nombre = nombre;
        this.establecimientos = new HashSet<>();
        this.localizacion = localizacion;
    }

    public Entidad() {

    }

    public List<PrestacionDeServicio> getPrestacionesDeServicios() {
        List<PrestacionDeServicio> prestaciones = new ArrayList<>();
        for (Establecimiento establecimiento : this.establecimientos) {
            prestaciones.addAll(establecimiento.getServicios());
        }
        return prestaciones;
    }

    public void agregarEstablecimiento(Establecimiento establecimiento) {
        this.establecimientos.add(establecimiento);
    }

}