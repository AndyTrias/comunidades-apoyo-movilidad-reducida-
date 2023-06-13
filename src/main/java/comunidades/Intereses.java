package comunidades;

import comunidades.servicios.Servicio;
import entidades.Entidad;

import java.util.List;
import java.util.Set;

public class Intereses {
    private Set<Servicio> servicios;
    private Set<Entidad> entidades;

    public boolean estaInteresado(Servicio servicio, Entidad entidad) {
        return servicios.contains(servicio) && entidades.contains(entidad);
    }

    public void agregarServicio(Servicio servicio) {
        servicios.add(servicio);
    }

    public void agregarEntidad(Entidad entidad) {
        entidades.add(entidad);
    }


}
