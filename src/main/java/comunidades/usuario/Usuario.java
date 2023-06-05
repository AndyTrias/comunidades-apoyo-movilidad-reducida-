package comunidades.usuario;

import comunidades.Comunidad;
import comunidades.Intereses;
import comunidades.Membresia;
import comunidades.Rol;
import localizacion.Localizacion;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Usuario {
    private String nombre;
    private String apellido;
    private String correoElectronico;
    @Getter private String contrasenia;
    private Intereses intereses;
    @Getter private List<Membresia> membresias;
    private Set<Localizacion> localizacion;

    public Usuario(String nombre, String apellido, String correoElectronico) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.contrasenia = contrasenia;
        this.intereses = intereses;
        this.membresias = new ArrayList<>();
    }

    public void setContrasenia(String contrasena) {
        // Implementaci�n
    }

    public void unirseAComunidad(Comunidad comunidad, Rol rol) {
        Membresia membresia = new Membresia(comunidad, rol);
        this.membresias.add(membresia);
    }

    public void abandonarComunidad(Comunidad comunidad) {
        membresias.remove(membresias.stream().filter(m -> m.getComunidad().equals(comunidad)).findFirst().get());
    }

}
