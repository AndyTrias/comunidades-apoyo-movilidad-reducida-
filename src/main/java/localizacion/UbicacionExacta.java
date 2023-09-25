package localizacion;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "ubicacion_exacta")
public class UbicacionExacta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "latitud")
    private double latitud;

    @Getter
    @Column
    private double longitud;

    public UbicacionExacta(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public UbicacionExacta() {

    }
}
