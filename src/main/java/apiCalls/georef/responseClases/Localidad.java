package apiCalls.georef.responseClases;

import javax.persistence.*;

@Entity
@Table(name = "localidad")

public class Localidad {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    public long id;
    @Column(name = "nombre")
    public String nombre;


    @ManyToOne
    @JoinColumn(name = "municipio_id")
    public Municipio municipio;
    @ManyToOne
    @JoinColumn(name = "provincia_id")
    public Provincia provincia;
}
