package servicios.clasesAuxiliares;

import lombok.Getter;

@Getter
public class Coordenadas {
    private double latitud;
    private double longitud;

    public Coordenadas(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
