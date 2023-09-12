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


    @BeforeEach
    void setUp() {
        repoUsuario = new RepoUsuario();
        repoComunidad = new RepoComunidad();
        repoServicio = new RepoServicio();
        repoPrestacion = new RepoPrestacion();
        repoEntidad = new RepoEntidad();
        repoEstablecimiento = new RepoEstablecimiento();
        repoIncidentes = new RepoIncidentes();
    }

    @Test
    void unirseAComunidad(){
        Usuario usuario = repoUsuario.buscar(1L);
        Comunidad comunidad = repoComunidad.buscar(1L);
        Rol rol = comunidad.aceptarUsuario(usuario);
        usuario.unirseAComunidad(comunidad, rol);
        repoUsuario.modificar(usuario);
    }

    @Test
    void persisteIncidenteTest(){
        Usuario usuario = repoUsuario.buscar(1L);
        PrestacionDeServicio prestacionDeServicio = repoPrestacion.buscar(1L);
        Comunidad comunidad = repoComunidad.buscar(1L);
        Incidente incidente = new Incidente(usuario, "baño sucio", prestacionDeServicio);
        comunidad.abrirIncidente(incidente);
        repoComunidad.agregar(comunidad);
    }

    @Test
    void cerrarIncidenteTest(){
        Usuario usuario = repoUsuario.buscar(1L);
        Comunidad comunidad = repoComunidad.buscar(1L);
        Incidente incidente = repoIncidentes.buscar(6L);
        comunidad.cerrarIncidente(incidente, usuario);
        repoComunidad.modificar(comunidad);
    }

    @Test
    void localizacionUsuarioTest(){
        Usuario usuario = repoUsuario.buscar(7L);
        System.out.println(usuario.getLocalizaciones());
    }

}
