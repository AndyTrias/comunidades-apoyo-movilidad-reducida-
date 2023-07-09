package comunidades.usuario;

import comunidades.Comunidad;
import comunidades.Intereses;
import comunidades.Membresia;
import comunidades.Rol;
import comunidades.usuario.Contrasenia.ValidadorDeContrasenia;
import configs.Config;
import configs.ServiceLocator;
import localizacion.Localizacion;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Usuario {
    private String nombre;
    private String apellido;
    @Getter @Setter private Email correoElectronico;
    @Getter private String contrasenia;
    @Getter @Setter private String telefono;
    @Getter private Intereses intereses;
    @Getter private List<Membresia> membresias;
    @Setter private Set<Localizacion> localizacion;

    public Usuario(String nombre, String apellido, Email correoElectronico) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.membresias = new ArrayList<>();
    }

    public void setContrasenia(String contrasenia) throws Exception {
        if (ServiceLocator.getValidador().validarContrasenia(contrasenia)) {
            this.contrasenia = contrasenia;
        }
        else {
            throw new Exception("La contraseña no es valida");
        }
    }

    public void unirseAComunidad(Comunidad comunidad, Rol rol) {
        Membresia membresia = new Membresia(comunidad, rol);
        this.membresias.add(membresia);
    }

    public void abandonarComunidad(Comunidad comunidad) throws Exception{
        if (membresias.stream().noneMatch(m -> m.getComunidad().equals(comunidad))) {
            throw new Exception("El usuario no pertenece a la comunidad");
        }
        else{
            membresias.remove(membresias.stream().filter(m -> m.getComunidad().equals(comunidad)).findFirst().get());
        }
    }

    public List<Comunidad> getComunidades(){
        List<Comunidad> comunidades = new ArrayList<>();
        membresias.forEach(m -> comunidades.add(m.getComunidad()));
        return comunidades;
    }
}
