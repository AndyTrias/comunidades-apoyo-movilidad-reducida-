package entidades;

import comunidades.servicios.PrestacionDeServicio;
import localizacion.Localizacion;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class Establecimiento {
    @Getter private String nombre;
    @Getter @Setter private Localizacion localizacion;
    @Getter private Set<PrestacionDeServicio> servicios;

    public Establecimiento(String nombre, Localizacion localizacion) {
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.servicios = new HashSet<>();
    }

    public void agregarServicioPrestado(PrestacionDeServicio servicio) {
        this.servicios.add(servicio);
    }
}
