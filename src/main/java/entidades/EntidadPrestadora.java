package entidades;

import comunidades.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class EntidadPrestadora {
    @Setter private Usuario personaDesignada;
    private List<Entidad> serviciosPrestados;
    @Getter private String nombre;
    @Getter private int id;

    public EntidadPrestadora(String nombre, int id){
        this.nombre = nombre;
        this.id = id;
    }


}
