package models.external.retrofit.georef.responseClases;

import java.util.List;

public class ListadoLocalidades {
    public int cantidad;
    public int inicio;
    public int total;
    public List<Localidad> localidades_censales;
    public Parametro parametros;

    public class Parametro {
        public List<String> campos;
        public int max;
        public List<String> provincias;
        public List<String> municipios;
    }
}
