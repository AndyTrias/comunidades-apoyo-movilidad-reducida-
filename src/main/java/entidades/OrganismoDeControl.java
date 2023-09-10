package entidades;

import usuario.Usuario;
import entidades.enviadorDeInformacion.AdapterEnviadorDeInformacion;
import lombok.Getter;

import java.util.List;

public class OrganismoDeControl {
    @Getter private String nombre;
    private Usuario personaDesignada;
    private List<EntidadPrestadora> entidadesQuePosee;
    private AdapterEnviadorDeInformacion enviadorDeInformacion;

    public OrganismoDeControl(String nombre){
        this.nombre = nombre;
    }

    public void enviarInformacion(){
        enviadorDeInformacion.enviarInformacion();
    }
}
