package comunidades;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Persona {
    private String nombre;

    private String apellido;

    @Setter
    private String correoElectronico;

    public Persona(String nombre, String apellido, String correoElectronico) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
    }

}
