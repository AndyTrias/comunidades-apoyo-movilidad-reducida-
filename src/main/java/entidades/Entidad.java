package entidades;

import localizacion.Localizacion;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Entidad {
    @Getter private Set<Establecimiento> establecimientos;
    @Setter private Localizacion localizacion;
    @Getter private String nombre;

    public Entidad(String nombre) {
        this.nombre = nombre;
        this.establecimientos = new HashSet<>();
    }

    public List<PrestacionDeServicio> getPrestacionesDeServicios() {
        List<PrestacionDeServicio> prestaciones = new ArrayList<>(PrestacionesDeServicio);
        for (Establecimiento establecimiento : this.establecimientos) {
            prestaciones.addAll(establecimiento.getServicios());
        }
        return prestaciones;
    }

    public void agregarEstablecimiento(Establecimiento establecimiento) {
        this.establecimientos.add(establecimiento);
    }

    public static void main(String[] args) {
        Entidad entidad = new Entidad("Entidad");
        System.out.println(entidad.getNombre());
    }


}
