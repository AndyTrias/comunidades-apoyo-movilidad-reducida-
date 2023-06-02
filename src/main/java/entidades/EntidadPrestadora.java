package entidades;

import comunidades.Usuario;
import lombok.Setter;

import java.util.List;

public class EntidadPrestadora {
    @Setter
    private Usuario personaDesignada;
    private List<Entidad> serviciosPrestados;

    public EntidadPrestadora(List<Entidad> serviciosPrestados) {
        this.serviciosPrestados = serviciosPrestados;
    }


}
