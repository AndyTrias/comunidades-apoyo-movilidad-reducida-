package apiCalls.georef.responseClases;

import java.util.List;

public class ListadoLocalidades {
    public int cantidad;
    public int inicio;
    public int total;
    public List<Localidad> localidades;

    public class Parametro {
        public List<String> campos;
        public int max;
        public List<String> provincia;
        public List<String> municipio;
    }
}
