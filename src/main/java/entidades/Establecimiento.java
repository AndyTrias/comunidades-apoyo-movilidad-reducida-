package entidades;

import comunidades.servicios.PrestacionDeServicio;
import localizacion.Localizacion;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public void agregarServicio(PrestacionDeServicio servicio) {
        this.servicios.add(servicio);
    }
}
