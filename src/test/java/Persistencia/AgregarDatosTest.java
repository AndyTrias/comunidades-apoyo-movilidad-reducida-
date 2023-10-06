package Persistencia;

import models.comunidades.Comunidad;
import models.entidades.Entidad;
import models.entidades.EntidadPrestadora;
import models.entidades.Establecimiento;
import models.entidades.OrganismoDeControl;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import models.localizacion.Localizacion;
import models.localizacion.UbicacionExacta;
import models.repositorios.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import repositiorios.*;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import models.usuario.Usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AgregarDatosTest {

    private RepoUsuario repoUsuario;
    private RepoComunidad repoComunidad;
    private RepoServicio repoServicio;
    private RepoEntidad repoEntidad;
    private RepoEstablecimiento repoEstablecimiento;
    private RepoEntidadPrestadora repoEntidadPrestadora;
    private RepoLocalizacion repoLocalizacion;
    private RepoOrganismoDeControl repoOrganismoDeControl;

    private Servicio servicio;
    private Comunidad comunidad;
    private Entidad entidad;
    private Establecimiento establecimiento;
    private EntidadPrestadora santander;


    @BeforeEach
    void setUp() {
        repoUsuario = new RepoUsuario();
        repoComunidad = new RepoComunidad();
        repoServicio = new RepoServicio();
        repoEntidad = new RepoEntidad();
        repoEstablecimiento = new RepoEstablecimiento();
        repoLocalizacion = new RepoLocalizacion();
        repoEntidadPrestadora = new RepoEntidadPrestadora();
        repoOrganismoDeControl = new RepoOrganismoDeControl();

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
    void agregarPrestacionAComunidad(){
        comunidad = repoComunidad.buscar(1L);
        Servicio servicio = repoServicio.buscar(1L);
        PrestacionDeServicio banioPalermo = new PrestacionDeServicio(servicio, "baño palermo", new UbicacionExacta(1, 1));
        comunidad.agregarServicioDeInteres(banioPalermo);
        repoComunidad.modificar(comunidad);
    }

    @Test
    void agregarServicio() {
        repoServicio.agregar(servicio);
    }

    @Test
    void agregarEntidadPrestadora(){
      EntidadPrestadora santander = new EntidadPrestadora("Santander Rio Argentina");
      santander.setPersonaDesignada(new Usuario());
      repoEntidadPrestadora.agregar(santander);
    }

    @Test
    void agregarOrganismoDeControl(){
        OrganismoDeControl bancos = new OrganismoDeControl("Bancos");
        bancos.setPersonaDesignada(new Usuario());
        repoOrganismoDeControl.agregar(bancos);
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
