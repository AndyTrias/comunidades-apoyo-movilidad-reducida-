package noServer.external.georef.responseClases;

import java.util.List;

public class ListadoProvincias {
    public int cantidad;
    public int inicio;
    public int total;
    public Parametro parametros;
    public List<Provincia> provincias;

    public class Parametro {
        public List<String> campos;
    }
}
