package entidades;

import comunidades.servicios.PrestacionDeServicio;
import localizacion.Localizacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "establecimiento")
public class Establecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private int id;

    @Column(name = "nombre")
    @Getter private String nombre;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter @Setter private Localizacion localizacion;

    @OneToMany(mappedBy = "establecimiento", cascade = CascadeType.ALL)
    @Getter private Set<PrestacionDeServicio> servicios;

    public Establecimiento(String nombre, Localizacion localizacion) {
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.servicios = new HashSet<>();
    }

    public void agregarServicioPrestado(PrestacionDeServicio servicio) {
        this.servicios.add(servicio);
    }
}
