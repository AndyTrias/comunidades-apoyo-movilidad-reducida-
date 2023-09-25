package external.georef.responseClases;


import javax.persistence.*;

import lombok.Setter;

@Entity
@Table(name = "municipio")
public class Municipio {
    @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int id;

    @Column(name = "nombre")
    public String nombre;

    @Setter
    @ManyToOne
    @JoinColumn(name = "provincia_id")
    public Provincia provincia;

}
