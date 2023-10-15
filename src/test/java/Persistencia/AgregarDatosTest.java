package Persistencia;

import models.comunidades.Comunidad;
import models.comunidades.Membresia;
import models.comunidades.Rol;
import models.comunidades.TipoRol;
import models.entidades.Entidad;
import models.entidades.EntidadPrestadora;
import models.entidades.Establecimiento;
import models.entidades.OrganismoDeControl;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import models.localizacion.Localizacion;
import models.localizacion.UbicacionExacta;
import models.repositorios.*;
import org.junit.jupiter.api.*;

//import repositiorios.*;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import models.usuario.Usuario;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AgregarDatosTest {

    private RepoUsuario repoUsuario;
    private RepoComunidad repoComunidad;
    private RepoServicio repoServicio;
    private RepoEntidad repoEntidad;
    private RepoEstablecimiento repoEstablecimiento;
    private RepoEntidadPrestadora repoEntidadPrestadora;
    private RepoLocalizacion repoLocalizacion;
    private RepoOrganismoDeControl repoOrganismoDeControl;
    private RepoPrestacion repoPrestacion;
    private RepoRol repoRol;

    private Servicio banio;
    private Entidad lineaB;
    private Establecimiento estacionMedrano;
    private Establecimiento estacionAlem;

    private Comunidad comunidad;

    private PrestacionDeServicio banioMedrano1;
    private PrestacionDeServicio banioMedrano2;
    private PrestacionDeServicio banioAlem;


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
        repoPrestacion = new RepoPrestacion();
        repoRol = new RepoRol();

        banio = new Servicio("baño");
        comunidad = new Comunidad("comunidad de baños del B");
        lineaB = new Entidad("Linea B", new Localizacion());
        estacionMedrano = new Establecimiento("Estacion Medrano", new Localizacion());
        estacionAlem = new Establecimiento("Estacion Alem", new Localizacion());

        

    }

    @Order(1)
    @Test
    void agregarServicio() {
        repoServicio.agregar(banio);
    }

    @Order(2)
    @Test
    void agregarEntidad() {
        lineaB.agregarEstablecimiento(estacionMedrano);
        lineaB.agregarEstablecimiento(estacionAlem);
        repoEntidad.agregar(lineaB);
    }

    @Order(3)
    @Test
    void agregarPrestacionAEstablecimiento() {
        Servicio banio = repoServicio.buscar(1L);
        
        banioMedrano1 = new PrestacionDeServicio(banio, "baño Medrano inferior", new UbicacionExacta(3, 3));
        banioMedrano2 = new PrestacionDeServicio(banio, "baño Medrano superior", new UbicacionExacta(2, 2));
        banioAlem = new PrestacionDeServicio(banio, "Baño Alem", new UbicacionExacta(1, 1));
        
        Entidad lineaB = repoEntidad.buscar(1L);
        estacionMedrano = lineaB.getEstablecimientos().stream().filter(establecimiento -> establecimiento.getNombre().equals("Estacion Medrano")).findFirst().get();

        
        estacionMedrano.agregarServicioPrestado(banioMedrano1);
        estacionMedrano.agregarServicioPrestado(banioMedrano2);

        estacionAlem = lineaB.getEstablecimientos().stream().filter(establecimiento -> establecimiento.getNombre().equals("Estacion Alem")).findFirst().get();
        estacionAlem.agregarServicioPrestado(banioAlem);

        repoEstablecimiento.modificar(estacionMedrano);
        repoEstablecimiento.modificar(estacionAlem);
    }

    @Order(4)
    @Test
    void agregarComunidad() {
        repoComunidad.agregar(comunidad);
    }

    @Order(5)
    @Test
    void agregarPrestacionAComunidad() {
        comunidad = repoComunidad.buscar(1L);
        List<PrestacionDeServicio> prestaciones = repoPrestacion.buscarTodos();

        // Use the agregarServiciosDeInteres method to add multiple prestations
        comunidad.agregarServiciosDeInteres(prestaciones.toArray(new PrestacionDeServicio[0]));

        repoComunidad.modificar(comunidad);
    }

//    @Test
//    void agregarEntidadPrestadora(){
//      EntidadPrestadora santander = new EntidadPrestadora("Santander Rio Argentina");
//      santander.setPersonaDesignada(new Usuario());
//      repoEntidadPrestadora.agregar(santander);
//    }
//
//    @Test
//    void agregarOrganismoDeControl(){
//        OrganismoDeControl bancos = new OrganismoDeControl("Bancos");
//        bancos.setPersonaDesignada(new Usuario());
//        repoOrganismoDeControl.agregar(bancos);
//    }

    @Order(6)
    @Test
    void agregarLocalizacion() throws Exception {
        Localizacion localizacion = new Localizacion();
        localizacion.setUbicacionAsLocalidad(6056010001L);
        repoLocalizacion.agregar(localizacion);
    }

    @Order(7)
    @Test
    void agregarUsuarioCompleto() throws Exception {
        Usuario usuario = new Usuario("franco", "pesce", "francopescee@gmail.com");
        usuario.setContrasenia("@ashffkrh3nksdnf214123cssdf");
        usuario.setTelefono("+5491131231231");
        usuario.setUbicacionExacta(new UbicacionExacta(1,1));
        usuario.setRol(repoRol.buscarPorNombre(TipoRol.ADMINISTRADOR_PLATAFORMA));
        repoUsuario.agregar(usuario);
    }

    @Order(8)
    @Test
    void agregarMembresia() throws Exception {
        Usuario usuario = repoUsuario.buscar(1L);
        Comunidad comunidad = repoComunidad.buscar(1L);

        Membresia membresia = new Membresia(comunidad, usuario, repoRol.buscarPorNombre(TipoRol.MIEMBRO));
        comunidad.agregarMembresia(membresia);
        usuario.unirseAComunidad(membresia);

        repoComunidad.modificar(comunidad);
    }

    /*
    @Order(9)
    @Test
    void agregarIncidenteARevisar(){
        Usuario usuario = repoUsuario.buscar(1L);
        Comunidad comunidad = repoComunidad.buscar(1L);

    }
     */
}
