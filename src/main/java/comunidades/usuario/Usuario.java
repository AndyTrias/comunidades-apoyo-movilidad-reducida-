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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Usuario {
    @Id
    @GeneratedValue
    private Long Id;
    private String nombre;
    private String apellido;
    @Setter private String correoElectronico;
    @Getter private String contrasenia;
    @Transient
    @Getter private Intereses intereses;
    @Transient
    @Getter private List<Membresia> membresias;
    @Transient
    @Setter private Set<Localizacion> localizacion;



    public Usuario() {

    }
    public Usuario(String nombre, String apellido, String correoElectronico) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.contrasenia = contrasenia;
        this.intereses = intereses;
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
}
