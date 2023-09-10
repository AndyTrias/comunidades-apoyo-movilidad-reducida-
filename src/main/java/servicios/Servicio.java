package servicios;

import lombok.Getter;
import lombok.Setter;

public class Servicio {
    @Getter @Setter private String nombre;

    public Servicio(String nombre) {
        this.nombre = nombre;
    }
}
