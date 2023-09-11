package Persistencia;

import comunidades.Comunidad;
import entidades.Entidad;
import entidades.Establecimiento;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import localizacion.Localizacion;
import localizacion.UbicacionExacta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositiorios.*;
import servicios.PrestacionDeServicio;
import servicios.Servicio;
import usuario.Usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AgregarDatosTest implements SimplePersistenceTest {

    private RepoUsuario repoUsuario;
    private RepoComunidad repoComunidad;
    private RepoServicio repoServicio;
    private RepoPrestacion repoPrestacion;
    private RepoEntidad repoEntidad;
    private RepoEstablecimiento repoEstablecimiento;

    private Servicio servicio;
    private Comunidad comunidad;
    private Entidad entidad;
    private Establecimiento establecimiento;

    @BeforeEach
    void setUp() {
        repoUsuario = new RepoUsuario();
        repoComunidad = new RepoComunidad();
        repoServicio = new RepoServicio();
        repoPrestacion = new RepoPrestacion();
        repoEntidad = new RepoEntidad();
        repoEstablecimiento = new RepoEstablecimiento();

        servicio = new Servicio("baño hombres");
        comunidad = new Comunidad("comunidad1");
        entidad = new Entidad("Santander Rio Argentina");
        establecimiento = new Establecimiento("Sucursal Almagro", new Localizacion());
    }

    @Test
    void agregarUsuario() {
        Usuario usuario = new Usuario("Juan", "Perez", "");
        repoUsuario.agregar(usuario);
    }

    @Test
    void agregarComunidad() {
        repoComunidad.agregar(comunidad);
    }

    @Test
    void agregarServicio() {
        repoServicio.agregar(servicio);
    }

    @Test
    void agregarPrestacion(){
        Servicio servicio = repoServicio.buscar(1L);
        PrestacionDeServicio banioMedrano = new PrestacionDeServicio(servicio, "baño Medrano", new UbicacionExacta(1, 1));
        repoPrestacion.agregar(banioMedrano);
    }

    @Test
    void agregarEntidad(){
        repoEntidad.agregar(entidad);
    }

    @Test
    void agregarEstablecimiento(){
        repoEstablecimiento.agregar(establecimiento);
    }

}
