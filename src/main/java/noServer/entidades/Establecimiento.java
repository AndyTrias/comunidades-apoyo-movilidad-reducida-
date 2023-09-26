package noServer.entidades;

import noServer.localizacion.Localizacion;
import noServer.servicios.PrestacionDeServicio;
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
    @Getter private String nombre;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter @Setter private Localizacion localizacion;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "establecimiento_id")
    @Getter private Set<PrestacionDeServicio> servicios;

    public Establecimiento(String nombre, Localizacion localizacion) {
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.servicios = new HashSet<>();
    }

    public Establecimiento() {

    }


    public void agregarServicioPrestado(PrestacionDeServicio servicio) {
        this.servicios.add(servicio);
    }
}
