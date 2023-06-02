package apiCalls.georef.responseClases;

import java.util.List;

public class UbicacionResponse {
    public int cantidad;
    public int inicio;
    public int total;
    public Parametro parametros;
    public Ubicacion provincias;

    public class Parametro {
        public List<String> campos;
    }
}
