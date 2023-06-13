package entidades;

import comunidades.usuario.Usuario;
import entidades.enviadorDeInformacion.AdapterEnviadorDeInformacion;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class EntidadPrestadora {
    @Getter private int id;
    @Getter private String nombre;
    @Setter private Usuario personaDesignada;
    private List<Entidad> serviciosPrestados;
    private AdapterEnviadorDeInformacion enviadorDeInformacion;

    public EntidadPrestadora(String nombre, int id){
        this.nombre = nombre;
        this.id = id;
    }

    public void enviarInformacion(){
        enviadorDeInformacion.enviarInformacion();
    }
}
