package models.rankings.informes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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


    public Informe(Date fecha, String path) {
        this.fecha = fecha;
        this.path = path;
    }

    public Informe() {
    }
}
