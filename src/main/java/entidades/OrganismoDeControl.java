package entidades;

import comunidades.usuario.Usuario;
import lombok.Getter;

import java.util.List;

public class OrganismoDeControl {
    @Getter private String nombre;
    private Usuario personaDesignada;
    private List<EntidadPrestadora> entidadesQuePosee;

    public OrganismoDeControl(String nombre){
        this.nombre = nombre;
    }
}
