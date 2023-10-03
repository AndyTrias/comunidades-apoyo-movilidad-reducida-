package models.usuario;

import models.comunidades.Comunidad;
import models.comunidades.Membresia;
import models.comunidades.Rol;
import models.usuario.configuraciones.ConfiguracionDeNotificaciones;
import models.configs.ServiceLocator;
import models.incidentes.RevisionDeIncidente;
import models.localizacion.Localizacion;
import models.localizacion.UbicacionExacta;
import lombok.Getter;
import lombok.Setter;
import models.notificaciones.Notificacion;
import models.usuario.configuraciones.formas.CuandoSuceden;
import models.usuario.configuraciones.medios.mail.NotificarPorMail;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private List<Interes> intereses;

    @Getter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true)
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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UbicacionExacta ubicacionExacta;

    public Usuario(String nombre, String apellido, String correoElectronico) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.membresias = new ArrayList<>();
        this.intereses = new ArrayList<>();

        this.localizaciones= new HashSet<>();

        this.configuracionDeNotificaciones = new ConfiguracionDeNotificaciones();
        configuracionDeNotificaciones.setEstrategiaDeNotificacion(new CuandoSuceden());
        configuracionDeNotificaciones.setMedioPreferido(new NotificarPorMail());

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

    public void agregarInteres(Interes interes) {
        this.intereses.add(interes);
    }

    public void agregarLocalizacion(Localizacion localizacion) {
        this.localizaciones.add(localizacion);
    }
}
