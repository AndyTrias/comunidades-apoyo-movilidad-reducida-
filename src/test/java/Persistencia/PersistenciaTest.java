package Persistencia;

import comunidades.Comunidad;
import comunidades.Rol;
import entidades.Entidad;
import entidades.Establecimiento;
import incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import localizacion.Localizacion;
import localizacion.UbicacionExacta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositiorios.*;
import servicios.PrestacionDeServicio;
import servicios.Servicio;
import usuario.Usuario;

public class PersistenciaTest implements SimplePersistenceTest {

    private RepoUsuario repoUsuario;
    private RepoComunidad repoComunidad;
    private RepoServicio repoServicio;
    private RepoPrestacion repoPrestacion;
    private RepoEntidad repoEntidad;
    private RepoEstablecimiento repoEstablecimiento;

    private RepoIncidentes repoIncidentes;

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
        repoIncidentes = new RepoIncidentes();

        servicio = new Servicio("baño hombres");
        comunidad = new Comunidad("comunidad1");
        entidad = new Entidad("Santander Rio Argentina");
        establecimiento = new Establecimiento("Sucursal Almagro", new Localizacion());
    }
    @Test
    void persisteIncidenteTest(){
        Usuario usuario = new Usuario("Juan", "Perez", " ");
        repoUsuario.agregar(usuario);

        Rol rol = comunidad.aceptarUsuario(usuario);
        usuario.unirseAComunidad(comunidad, rol);
        PrestacionDeServicio banioMedrano = new PrestacionDeServicio(servicio, "baño Medrano", new UbicacionExacta(1, 1));
        comunidad.agregarServicioDeInteres(banioMedrano);
        Incidente incidente = new Incidente(usuario, "baño sucio", banioMedrano);
        comunidad.abrirIncidente(incidente);
        repoIncidentes.agregar(incidente);
        //repoComunidad.agregar(comunidad);


    }

}
