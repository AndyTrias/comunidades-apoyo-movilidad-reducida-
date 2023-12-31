/*
package Persistencia;

import models.comunidades.Membresia;
import models.entidades.Entidad;
import models.entidades.EntidadPrestadora;
import models.entidades.Establecimiento;
import models.localizacion.Localizacion;
import models.repositorios.*;
import models.usuario.configuraciones.formas.SinApuros;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.comunidades.Comunidad;
import models.usuario.Rol;
import models.incidentes.Incidente;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import models.usuario.Interes;
import models.usuario.Usuario;

import java.util.Date;

public class PersistenciaTest {

    private RepoUsuario repoUsuario;
    private RepoComunidad repoComunidad;
    private RepoPrestacion repoPrestacion;
    private RepoIncidentes repoIncidentes;
    private RepoLocalizacion repoLocalizacion;
    private RepoEntidadPrestadora repoEntidadPrestadora;

    private Servicio servicio;
    private Entidad entidad;
    private Establecimiento establecimiento;

    @BeforeEach
    void setUp() {
        repoUsuario = new RepoUsuario();
        repoComunidad = new RepoComunidad();
        repoPrestacion = new RepoPrestacion();
        repoIncidentes = new RepoIncidentes();
        repoLocalizacion = new RepoLocalizacion();
        repoEntidadPrestadora = new RepoEntidadPrestadora();

        servicio = new Servicio("baño hombres");
        entidad = new Entidad("Santander Rio Argentina", new Localizacion());
        establecimiento = new Establecimiento("Sucursal Almagro", new Localizacion());
    }

    @Test
    void unirseAComunidad(){
        Usuario usuario = repoUsuario.buscar(3L);
        Comunidad comunidad = repoComunidad.buscar(1L);

        Membresia membresiaNueva = new Membresia(comunidad, usuario, new Rol());
        usuario.unirseAComunidad(membresiaNueva);
        comunidad.agregarMembresia(membresiaNueva);

        repoUsuario.modificar(usuario);
        repoComunidad.modificar(comunidad);
    }

    @Test
    void persisteIncidenteTest(){
        Usuario usuario = repoUsuario.buscar(3L);
        PrestacionDeServicio prestacionDeServicio = repoPrestacion.buscar(1L);
        Comunidad comunidad = repoComunidad.buscar(1L);
        Incidente incidente = new Incidente(usuario, "baño sucio", prestacionDeServicio, new Date());
        comunidad.abrirIncidente(incidente);
        repoComunidad.agregar(comunidad);
    }

    @Test
    void cerrarIncidenteTest(){
        Usuario usuario = repoUsuario.buscar(1L);
        Comunidad comunidad = repoComunidad.buscar(1L);
        Incidente incidente = repoIncidentes.buscar(2L);
        comunidad.cerrarIncidente(incidente, usuario);
        repoComunidad.modificar(comunidad);
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
        Usuario usuario = repoUsuario.buscar(3L);
        Interes interes = new Interes();
        interes.setEntidad(entidad);
        interes.setServicio(servicio);
        usuario.agregarInteres(interes);
        repoUsuario.modificar(usuario);
    }

    @Test
    void agregarPermisoARol(){
//        Comunidad comunidad = repoComunidad.buscar(1L);
//        Rol rolBase = comunidad.getRoles().get(0);
//        Permiso leer = new Permiso();
//        leer.setNombre("leer");
//        rolBase.agregarPermiso(leer);
//        repoComunidad.modificar(comunidad);
    }

    @Test
    void borrarLocalizacion() { //cascada con ubicacion
        Localizacion localizacion = repoLocalizacion.buscar(1L);
        repoLocalizacion.eliminar(localizacion);
    }

    @Test
    void agregarEntidad(){//agrega 2 ubicaciones sin datos y 2 localizacion relacionada
        entidad.agregarEstablecimiento(establecimiento);
        EntidadPrestadora santander = repoEntidadPrestadora.buscar(1L);
        santander.agregarEntidad(entidad);
        repoEntidadPrestadora.agregar(santander);
    }

    @Test
    void agregarNotificacionSinApuros(){
        Usuario usuario = repoUsuario.buscar(1L);

        Date horario = new Date();
        horario.setMinutes(21);
        SinApuros sinApuros = new SinApuros(horario);
        usuario.getConfiguracionDeNotificaciones().setEstrategiaDeNotificacion(sinApuros);
        repoUsuario.modificar(usuario);

    }

    @Test
    void asd(){
        Usuario usuario2 = repoUsuario.buscar(1L);

    }
}
*/
