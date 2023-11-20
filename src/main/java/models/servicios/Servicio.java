package models.servicios;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    @Column(name = "nombre")
    private String nombre;

    public Servicio(String nombre) {
        this.nombre = nombre;
    }

    public Servicio() {

    }
}
