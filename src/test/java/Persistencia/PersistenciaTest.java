package Persistencia;

import comunidades.Permiso;
import entidades.Entidad;
import localizacion.Localizacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import comunidades.Comunidad;
import comunidades.Rol;
import incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import repositiorios.*;
import servicios.PrestacionDeServicio;
import servicios.Servicio;
import usuario.Interes;
import usuario.Usuario;

public class PersistenciaTest implements SimplePersistenceTest {

    private RepoUsuario repoUsuario;
    private RepoComunidad repoComunidad;
    private RepoPrestacion repoPrestacion;
    private RepoIncidentes repoIncidentes;
    private RepoLocalizacion repoLocalizacion;

    private Servicio servicio;
    private Entidad entidad;

    @BeforeEach
    void setUp() {
        repoUsuario = new RepoUsuario();
        repoComunidad = new RepoComunidad();
        repoPrestacion = new RepoPrestacion();
        repoIncidentes = new RepoIncidentes();
        repoLocalizacion = new RepoLocalizacion();

        servicio = new Servicio("baño hombres");
        entidad = new Entidad("Santander Rio Argentina", new Localizacion());
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
    void borrarUsuario() {
        Usuario usuario = repoUsuario.buscar(1L);
        repoUsuario.eliminar(usuario);
    }

    @Test
    void agregarLocalizacionUsuario() {
        Usuario usuario = repoUsuario.buscar(1L);
        Localizacion localizacion = repoLocalizacion.buscar(1L);
        usuario.agregarLocalizacion(localizacion);
        repoUsuario.modificar(usuario);
    }

    @Test
    void agregarInteresAUsuario(){//agrega una ubicacion sin datos y una localizacion relacionada
        Usuario usuario = repoUsuario.buscar(1L);
        Interes interes = new Interes();
        interes.setEntidad(entidad);
        interes.setServicio(servicio);
        usuario.agregarInteres(interes);
        repoUsuario.modificar(usuario);
    }

    @Test
    void agregarPermisoARol(){
        Comunidad comunidad = repoComunidad.buscar(1L);
        Rol rolBase = comunidad.getRoles().get(0);
        Permiso leer = new Permiso();
        leer.setNombre("leer");
        rolBase.agregarPermiso(leer);
        repoComunidad.modificar(comunidad);
    }

}
