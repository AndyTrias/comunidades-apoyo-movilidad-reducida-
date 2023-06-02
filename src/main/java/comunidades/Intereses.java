package comunidades;

import comunidades.servicios.Servicio;
import entidades.Entidad;

import java.util.List;

public class Intereses {
    private List<Servicio> servicios;
    private List<Entidad> entidades;

    public boolean estaInteresado(Servicio servicio, Entidad entidad) {
        return true;
    }
}
