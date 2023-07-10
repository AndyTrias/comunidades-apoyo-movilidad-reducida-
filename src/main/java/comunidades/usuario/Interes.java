package comunidades.usuario;

import servicios.Servicio;
import entidades.Entidad;
import lombok.Getter;

import java.util.Set;

public class Interes {
    @Getter private Set<Servicio> servicios;
    @Getter Set<Entidad> entidades;

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
