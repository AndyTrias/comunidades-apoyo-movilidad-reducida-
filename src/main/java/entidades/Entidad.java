package entidades;

import servicios.PrestacionDeServicio;
import localizacion.Localizacion;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Getter
    @OneToMany
    @JoinColumn(name = "entidad_id")
    private Set<Establecimiento> establecimientos;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private Localizacion localizacion;

    @Getter
    @Column(name = "nombre")
    private String nombre;


    public Entidad(String nombre) {
        this.nombre = nombre;
        this.establecimientos = new HashSet<>();
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

    public static void main(String[] args) {
        Entidad entidad = new Entidad("Entidad");
        System.out.println(entidad.getNombre());
    }


}
