package models.rankings.informes;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "informe")
public class Informe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Column(name = "fecha", nullable = false)
    public Date fecha;

    @Getter
    @Column(name = "path", nullable = false)
    public String path;

    @Getter
    @Column(name = "nombre", nullable = false)
    public String nombre;

    public Informe(Date fecha, String path, String nombre) {
        this.fecha = fecha;
        this.path = path;
        this.nombre = nombre;
    }

    public Informe() {
    }
}
