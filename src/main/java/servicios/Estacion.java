package servicios;

import lombok.Getter;
import servicios.clasesAuxiliares.Coordenadas;
import servicios.serviciosPublicos.Servicio;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Estacion {
    private String nombre;
    private Coordenadas ubicacionGeografica;
    private Set<Servicio> servicios;

    public Estacion(String nombre, Coordenadas ubicacionGeografica, Set<Servicio> servicios) {
        this.nombre = nombre;
        this.ubicacionGeografica = ubicacionGeografica;
        this.servicios = servicios;
    }

    public boolean todosLosServiciosFuncionan() {
        return servicios.stream().allMatch(servicio -> servicio.isFunciona());
    }

    public Set<Servicio> serviciosQueNoFuncionan() {
        return servicios.stream().filter(servicio -> !servicio.isFunciona()).collect(Collectors.toSet());
    }
}
