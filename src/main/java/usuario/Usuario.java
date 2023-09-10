package usuario;

import comunidades.Comunidad;
import comunidades.Membresia;
import comunidades.Rol;
import usuario.configuraciones.ConfiguracionDeNotificaciones;
import configs.ServiceLocator;
import incidentes.RevisionDeIncidente;
import localizacion.Localizacion;
import localizacion.UbicacionExacta;
import lombok.Getter;
import lombok.Setter;
import notificaciones.Notificacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Getter
    @Setter
    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Getter
    @Column(name = "contrasenia")
    private String contrasenia;

    @Getter
    @Setter
    @Column(name = "telefono")
    private String telefono;

    @Getter
    @OneToOne
    private Interes interes;

    @Getter
    @OneToMany
    @JoinColumn(name = "usuario_id")
    private List<Membresia> membresias;

    @Getter
    @Setter
    @Transient
    private Set<Localizacion> localizaciones;

    @Getter
    @Setter
    @Transient
    private ConfiguracionDeNotificaciones configuracionDeNotificaciones;

    @Getter
    @Transient
    private UbicacionExacta ubicacionExacta;

    public Usuario(String nombre, String apellido, String correoElectronico) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.membresias = new ArrayList<>();
        this.interes = new Interes();
    }

    public Usuario() {}

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

    public Membresia getMembresia(Comunidad comunidad){
        return membresias.stream().filter(m -> m.getComunidad().equals(comunidad)).findFirst().get();
    }

    public void notificar(Notificacion notificacion) {
        configuracionDeNotificaciones.notificar(notificacion);
    }

    public void setUbicacionExacta(UbicacionExacta ubicacionExacta) {
        this.ubicacionExacta = ubicacionExacta;
        RevisionDeIncidente.getInstance().comprobarCercania(this);
    }
}
