package noServer.external.georef.responseClases;

import javax.persistence.*;

@Entity
@Table(name = "provincia")
public class Provincia {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int id;

    @Column(name = "nombre")
    public  String nombre;

    public Provincia() {
    }
}