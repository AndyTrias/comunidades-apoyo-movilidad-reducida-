package models.usuario;

import models.comunidades.Comunidad;
import models.comunidades.Membresia;
import models.comunidades.Rol;
import models.comunidades.TipoRol;
import models.incidentes.Incidente;
import models.usuario.configuraciones.ConfiguracionDeNotificaciones;
import models.configs.ServiceLocator;
import models.localizacion.Localizacion;
import models.localizacion.UbicacionExacta;
import lombok.Getter;
import lombok.Setter;
import models.notificaciones.Notificacion;
import models.usuario.configuraciones.formas.CuandoSuceden;
import models.usuario.configuraciones.medios.mail.NotificarPorMail;
import server.exceptions.CredencialesInvalidaException;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Getter
    @Setter
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Getter
    @Setter
    @Column(name = "correo_electronico", nullable = false)
    private String correoElectronico;

    @Getter
    @Column(name = "contrasenia", nullable = false)
    private String contrasenia;

    @Getter
    @Setter
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Getter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private List<Interes> intereses;

    @Getter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Membresia> membresias;

    @Getter
    @Setter
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Set<Localizacion> localizaciones;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private ConfiguracionDeNotificaciones configuracionDeNotificaciones;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UbicacionExacta ubicacionExacta;


    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Rol rol;

    @Getter
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Incidente> revisionDeIncidentes;

    public Usuario(String nombre, String apellido, String correoElectronico) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.membresias = new ArrayList<>();
        this.intereses = new ArrayList<>();
        this.localizaciones= new HashSet<>();
        this.revisionDeIncidentes = new ArrayList<>();

        this.configuracionDeNotificaciones = new ConfiguracionDeNotificaciones();
        configuracionDeNotificaciones.setEstrategiaDeNotificacion(new CuandoSuceden());
        configuracionDeNotificaciones.setMedioPreferido(new NotificarPorMail());

    }

    public Usuario() {
    }

    public void setContrasenia(String contrasenia) throws CredencialesInvalidaException {
        if (ServiceLocator.getValidadorCompleto().validarContrasenia(contrasenia)) {
            this.contrasenia = contrasenia;
        }
        else {
            throw new CredencialesInvalidaException("La contraseña no es segura");
        }
    }

    public void unirseAComunidad(Membresia membresia) {
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
        return membresias.stream().map(Membresia::getComunidad).toList();
    }

    public Membresia getMembresia(Comunidad comunidad){
        return membresias.stream().filter(m -> m.getComunidad().getId().equals(comunidad.getId())).findFirst().get();
    }

    public void notificar(Notificacion notificacion) {
        configuracionDeNotificaciones.notificar(notificacion);
    }

    public void agregarInteres(Interes interes) {
        this.intereses.add(interes);
    }

    public void agregarLocalizacion(Localizacion localizacion) {
        this.localizaciones.add(localizacion);
    }

    public void agregarRevisionDeIncidente(Incidente incidente) {
        this.revisionDeIncidentes.add(incidente);
    }

    public TipoRol getTipoRol() { return rol.getTipoRol(); }
}
