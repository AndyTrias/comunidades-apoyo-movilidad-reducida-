package entidades;

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
    @Getter @Setter private int id;
    @OneToMany(mappedBy = "entidad", cascade = CascadeType.ALL)
    @Getter private Set<Establecimiento> establecimientos;
    @OneToOne(cascade = CascadeType.ALL)
    @Setter private Localizacion localizacion;
    @Column(name = "nombre")
    @Getter private String nombre;


    public Entidad(String nombre) {
        this.nombre = nombre;
        this.establecimientos = new HashSet<>();
    }

    public void agregarEstablecimiento(Establecimiento establecimiento) {
        this.establecimientos.add(establecimiento);
    }

    public static void main(String[] args) {
        Entidad entidad = new Entidad("Entidad");
        System.out.println(entidad.getNombre());
    }
}
