package entidades;

import localizacion.Localizacion;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//comente cosas para probar lo del csv porque por ahora solo instancio en nombre de la clase
public class Entidad {
    private Set<Establecimiento> establecimientos;
    @Setter private Localizacion localizacion;
    @Getter private String nombre;

    public Entidad(String nombre) {
        this.nombre = nombre;
        this.establecimientos = new HashSet<>();
    }

    public void agregarEstablecimiento(Establecimiento establecimiento) {
        this.establecimientos.add(establecimiento);
    }
}
