package Persistencia;

import comunidades.Comunidad;
import comunidades.Permiso;
import comunidades.Rol;
import entidades.Entidad;
import entidades.Establecimiento;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import localizacion.Localizacion;
import localizacion.UbicacionExacta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.twilio.rest.api.v2010.account.incomingphonenumber.Local;

import repositiorios.*;
import servicios.PrestacionDeServicio;
import servicios.Servicio;
import usuario.Interes;
import usuario.Usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AgregarDatosTest implements SimplePersistenceTest {

    private RepoUsuario repoUsuario;
    private RepoComunidad repoComunidad;
    private RepoServicio repoServicio;
    private RepoEntidad repoEntidad;
    private RepoEstablecimiento repoEstablecimiento;

    private RepoLocalizacion repoLocalizacion;

    private Servicio servicio;
    private Comunidad comunidad;
    private Entidad entidad;
    private Establecimiento establecimiento;

    @BeforeEach
    void setUp() {
        repoUsuario = new RepoUsuario();
        repoComunidad = new RepoComunidad();
        repoServicio = new RepoServicio();
        repoEntidad = new RepoEntidad();
        repoEstablecimiento = new RepoEstablecimiento();
        repoLocalizacion = new RepoLocalizacion();

        servicio = new Servicio("baño hombres");
        comunidad = new Comunidad("comunidad1");
        entidad = new Entidad("Santander Rio Argentina", new Localizacion());
        establecimiento = new Establecimiento("Sucursal Almagro", new Localizacion());
    }

    @Test
    void agregarComunidad() {
        Servicio servicio = repoServicio.buscar(1L);
        PrestacionDeServicio banioMedrano = new PrestacionDeServicio(servicio, "baño Medrano", new UbicacionExacta(1, 1));
        comunidad.agregarServicioDeInteres(banioMedrano);
        repoComunidad.agregar(comunidad);
    }

    @Test
    void agregarServicio() {
        repoServicio.agregar(servicio);
    }

    @Test
    void agregarEntidad(){//agrega 2 ubicaciones sin datos y 2 localizacion relacionada
        entidad.agregarEstablecimiento(establecimiento);
        repoEntidad.agregar(entidad);
    }

    @Test
    void agregarEstablecimiento(){//agrega una ubicacion sin datos y una localizacion relacionada
        PrestacionDeServicio prestacionDeServicio = new PrestacionDeServicio(servicio, "baño Medrano", new UbicacionExacta(1, 1));
        establecimiento.agregarServicioPrestado(prestacionDeServicio);
        repoEstablecimiento.agregar(establecimiento);
    }

    @Test
    void agregarLocalizacion() throws Exception {
        Localizacion localizacion = new Localizacion();
        localizacion.setUbicacionAsLocalidad(6056010001L);
        repoLocalizacion.agregar(localizacion);
    }

    @Test
    void agregarUsuarioCompleto() throws Exception {
        Usuario usuario = new Usuario("franco", "pesce", "francopescee@gmail.com");
        usuario.setContrasenia("@ashffkrh3nksdnf214123cssdf");
        usuario.setTelefono("+5491131231231");
        usuario.setUbicacionExacta(new UbicacionExacta(1,1));
        repoUsuario.agregar(usuario);
    }
}
