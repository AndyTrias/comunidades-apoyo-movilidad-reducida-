package entidades;

import comunidades.usuario.Usuario;

import java.util.List;

public class OrganismoDeControl {
    private String nombre;
    private Usuario personaDesignada;
    private List<EntidadPrestadora> entidadesQuePosee;

    public OrganismoDeControl(String nombre){
        this.nombre = nombre;
    }
}
