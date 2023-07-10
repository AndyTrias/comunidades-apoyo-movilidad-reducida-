package localizacion;

public class UbicacionExacta {
    private double latitud;
    private double longitud;

    public UbicacionExacta(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return this.latitud;
    }

    public double getLongitud() {
        return this.longitud;
    }
}
