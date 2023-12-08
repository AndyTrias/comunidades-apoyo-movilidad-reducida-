package Persistencia;

import models.comunidades.Comunidad;
import models.comunidades.Membresia;
import models.usuario.TipoRol;
import models.entidades.Entidad;
import models.entidades.EntidadPrestadora;
import models.entidades.Establecimiento;
import models.entidades.OrganismoDeControl;
import models.incidentes.Incidente;
import models.localizacion.Localizacion;
import models.localizacion.UbicacionExacta;
import models.repositorios.*;
import org.junit.jupiter.api.*;

//import repositiorios.*;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import models.usuario.Usuario;

import java.util.Date;
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

    private RepoIncidentes repoIncidentes;


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
        repoIncidentes = new RepoIncidentes();

    }

    @Order(1)
    @Test
    void agregarServicio() {
        Servicio banio = new Servicio("Baño");
        Servicio escalera = new Servicio("Escalera mecanica");
        repoServicio.agregar(banio);
        repoServicio.agregar(escalera);
    }

    @Order(2)
    @Test
    void agregarOrganismoDeControlyPrestadoras() {
        OrganismoDeControl ciudad = new OrganismoDeControl("Comisión Nacional de Regulación del Transporte");
        EntidadPrestadora trenesArgentinos = new EntidadPrestadora("Trenes Argentinos");
        EntidadPrestadora metrovias = new EntidadPrestadora("Metrovías S.A.");
        ciudad.agregarPrestadora(trenesArgentinos);
        ciudad.agregarPrestadora(metrovias);

        repoOrganismoDeControl.agregar(ciudad);
    }


    @Order(3)
    @Test
    void agregarEntidades() {
        Localizacion localizacion = new Localizacion();
        Servicio servicio = repoServicio.buscar(1L);

        Entidad lineaA = new Entidad("Linea Mitre", localizacion);
        EntidadPrestadora prestadora = repoEntidadPrestadora.buscar(1L);
        prestadora.agregarEntidades(lineaA);

        Establecimiento retiro = new Establecimiento("Estacion Retiro", localizacion);
        lineaA.agregarEstablecimiento(retiro);
        Establecimiento belgranoC = new Establecimiento("Estacion Belgrano C", localizacion);
        lineaA.agregarEstablecimiento(belgranoC);

        PrestacionDeServicio baniobelgranoC = new PrestacionDeServicio(servicio, "Baño Belgrano C", new UbicacionExacta(-58.44937311888542, -34.55760118182227));
        retiro.agregarServicioPrestado(baniobelgranoC);
        PrestacionDeServicio banioRetiro = new PrestacionDeServicio(servicio, "Baño Retiro", new UbicacionExacta(-58.37491306829704, -34.59115726819361));
        belgranoC.agregarServicioPrestado(banioRetiro);


        repoEntidadPrestadora.modificar(prestadora);
    }

    @Order(4)
    @Test
    void agregarEstacionesLineaRoca() {
        Localizacion localizacion = new Localizacion();
        Servicio servicio = repoServicio.buscar(1L);

        Entidad lineaRoca = new Entidad("Linea Roca", localizacion);

        EntidadPrestadora prestadora = repoEntidadPrestadora.buscar(1L);
        prestadora.agregarEntidades(lineaRoca);

        Establecimiento estacionKostekiSantillan = new Establecimiento("Estacion Kosteki y Santillán", localizacion);
        lineaRoca.agregarEstablecimiento(estacionKostekiSantillan);

        Establecimiento estacionAvellaneda = new Establecimiento("Estacion Avellaneda", localizacion);
        lineaRoca.agregarEstablecimiento(estacionAvellaneda);

        PrestacionDeServicio banoEstacionKostekiSantillan = new PrestacionDeServicio(servicio, "Baño - Estacion Kosteki y Santillán", new UbicacionExacta(-58.376587215544404, -34.66115614252646));
        estacionKostekiSantillan.agregarServicioPrestado(banoEstacionKostekiSantillan);

        PrestacionDeServicio banoEstacionAvellaneda = new PrestacionDeServicio(servicio, "Baño - Estacion Avellaneda", new UbicacionExacta(-58.37133069407539, -34.66996497542504));
        estacionAvellaneda.agregarServicioPrestado(banoEstacionAvellaneda);

        repoEntidadPrestadora.modificar(prestadora);
    }

    @Order(5)
    @Test
    void agregarEstacionesLineaBelgrano() {
        Localizacion localizacion = new Localizacion();
        Servicio servicio = repoServicio.buscar(1L); // Asegúrate de que el ID del servicio sea correcto

        Entidad lineaBelgrano = new Entidad("Linea Belgrano", localizacion); // Crea la entidad si no existe

        EntidadPrestadora prestadora = repoEntidadPrestadora.buscar(1L);
        prestadora.agregarEntidades(lineaBelgrano);

        Establecimiento estacionRetiro = new Establecimiento("Estacion Rafael Castillo", localizacion);
        lineaBelgrano.agregarEstablecimiento(estacionRetiro);

        Establecimiento estacionCiudadJardin = new Establecimiento("Estacion Ciudad Jardin", localizacion);
        lineaBelgrano.agregarEstablecimiento(estacionCiudadJardin);

        PrestacionDeServicio banoEstacionRetiro = new PrestacionDeServicio(servicio, "Baño - Estacion Rafael Castillo", new UbicacionExacta(-58.626473115905426, -34.69858414548738));
        estacionRetiro.agregarServicioPrestado(banoEstacionRetiro);

        PrestacionDeServicio banoEstacionCiudadJardin = new PrestacionDeServicio(servicio, "Baño - Estacion Ciudad Jardin", new UbicacionExacta(-58.595324846961276, -34.604819192026895));
        estacionCiudadJardin.agregarServicioPrestado(banoEstacionCiudadJardin);

        repoEntidadPrestadora.modificar(prestadora);
    }

    @Order(6)
    @Test
    void agregarEstacionesLineaUrquiza() {
        Localizacion localizacion = new Localizacion();
        Servicio servicio = repoServicio.buscar(1L); // Asegúrate de que el ID del servicio sea correcto

        Entidad lineaUrquiza = new Entidad("Línea Urquiza", localizacion); // Crea la entidad si no existe

        EntidadPrestadora prestadora = repoEntidadPrestadora.buscar(2L); // Utiliza 2L para la EntidadPrestadora de Metrovías S.A.
        prestadora.agregarEntidades(lineaUrquiza);

        Establecimiento estacionLugano = new Establecimiento("Estación Lugano", localizacion);
        lineaUrquiza.agregarEstablecimiento(estacionLugano);

        Establecimiento estacionMitre = new Establecimiento("Estación Mitre", localizacion);
        lineaUrquiza.agregarEstablecimiento(estacionMitre);

        PrestacionDeServicio banoEstacionLugano = new PrestacionDeServicio(servicio, "Baño Lugano", new UbicacionExacta(-58.47689828500522, -34.677405669173204));
        estacionLugano.agregarServicioPrestado(banoEstacionLugano);

        PrestacionDeServicio banoEstacionMitre = new PrestacionDeServicio(servicio, "Baño Mitre", new UbicacionExacta(-58.44075451951553, -34.62922995767716));
        estacionMitre.agregarServicioPrestado(banoEstacionMitre);

        repoEntidadPrestadora.modificar(prestadora);
    }

    @Test
    @Order(7)
    void agregarEstacionesOtraLinea() {
        Localizacion localizacion = new Localizacion();
        Servicio servicio = repoServicio.buscar(1L); // Asegúrate de que el ID del servicio sea correcto

        Entidad otraLinea = new Entidad("Otra Línea Metrovías S.A.", localizacion); // Crea la entidad si no existe

        EntidadPrestadora prestadora = repoEntidadPrestadora.buscar(2L); // Utiliza 2L para la EntidadPrestadora de Metrovías S.A.
        prestadora.agregarEntidades(otraLinea);

        Establecimiento estacion1 = new Establecimiento("Estación 1 Metrovias", localizacion);
        otraLinea.agregarEstablecimiento(estacion1);

        Establecimiento estacion2 = new Establecimiento("Estación 2 Metrovias", localizacion);
        otraLinea.agregarEstablecimiento(estacion2);

        PrestacionDeServicio banoEstacion1 = new PrestacionDeServicio(servicio, "Baño Estación 1", new UbicacionExacta(1, 1));
        estacion1.agregarServicioPrestado(banoEstacion1);

        PrestacionDeServicio banoEstacion2 = new PrestacionDeServicio(servicio, "Baño Estación 2", new UbicacionExacta(1, 1));
        estacion2.agregarServicioPrestado(banoEstacion2);

        repoEntidadPrestadora.modificar(prestadora);
    }


    @Order(8)
    @Test
    void agregarComunidad() {
        Comunidad comunidad = new Comunidad("Baños de Trenes Argentinos");
        Comunidad comunidad1 = new Comunidad("Baños de Metrovias");
        Comunidad comunidad2 = new Comunidad("Baños de La linea Mitre");
        Comunidad comunidad3 = new Comunidad("Servicios de Belgrano C");
        repoComunidad.agregar(comunidad);
        repoComunidad.agregar(comunidad1);
        repoComunidad.agregar(comunidad2);
        repoComunidad.agregar(comunidad3);
    }

    @Order(9)
    @Test
    void agregarPrestacionAComunidad() {
        Comunidad comunidad = repoComunidad.buscar(1L);
        Comunidad comunidad1 = repoComunidad.buscar(2L);
        Comunidad comunidad2 = repoComunidad.buscar(3L);
        Comunidad comunidad3 = repoComunidad.buscar(4L);

        PrestacionDeServicio prestacionDeServicio = repoPrestacion.buscar(1L);
        PrestacionDeServicio prestacionDeServicio1 = repoPrestacion.buscar(2L);
        PrestacionDeServicio prestacionDeServicio2 = repoPrestacion.buscar(3L);
        PrestacionDeServicio prestacionDeServicio3 = repoPrestacion.buscar(4L);
        PrestacionDeServicio prestacionDeServicio4 = repoPrestacion.buscar(5L);
        PrestacionDeServicio prestacionDeServicio5 = repoPrestacion.buscar(6L);

        comunidad.agregarServiciosDeInteres(prestacionDeServicio, prestacionDeServicio1, prestacionDeServicio2, prestacionDeServicio3, prestacionDeServicio4, prestacionDeServicio5);
        repoComunidad.modificar(comunidad);

        PrestacionDeServicio prestacionDeServicio6 = repoPrestacion.buscar(7L);
        PrestacionDeServicio prestacionDeServicio7 = repoPrestacion.buscar(8L);
        PrestacionDeServicio prestacionDeServicio8 = repoPrestacion.buscar(9L);
        PrestacionDeServicio prestacionDeServicio9 = repoPrestacion.buscar(10L);
        comunidad1.agregarServiciosDeInteres(prestacionDeServicio6, prestacionDeServicio7, prestacionDeServicio8, prestacionDeServicio9);

        repoComunidad.modificar(comunidad1);

        comunidad2.agregarServiciosDeInteres(prestacionDeServicio, prestacionDeServicio1);
        repoComunidad.modificar(comunidad2);

        comunidad3.agregarServiciosDeInteres(prestacionDeServicio);
        repoComunidad.modificar(comunidad3);
    }

    @Order(10)
    @Test
    void agregarLocalizacion() throws Exception {
        Localizacion localizacion = new Localizacion();
        localizacion.setUbicacionAsLocalidad(6056010001L);
        repoLocalizacion.agregar(localizacion);
    }

    @Order(11)
    @Test
    void agregarUsuarioCompleto() throws Exception {
        Usuario usuario = new Usuario("franco", "pesce", "francopescee@gmail.com");
        usuario.setContrasenia("@ashffkrh3nksdnf214123cssdf");
        usuario.setTelefono("+5491131231231");
        usuario.setUbicacionExacta(new UbicacionExacta(1, 1));
        usuario.setRol(repoRol.buscarPorNombre(TipoRol.ADMINISTRADOR_PLATAFORMA));
        repoUsuario.agregar(usuario);

        Usuario usuario2 = new Usuario("Gian", "Riccelli", "yayoriccelli@gmail.com");
        usuario2.setContrasenia("@ashffkrh3nksdnf214123cssdf");
        usuario2.setTelefono("+5491131231231");
        usuario2.setUbicacionExacta(new UbicacionExacta(1, 1));
        usuario2.setRol(repoRol.buscarPorNombre(TipoRol.MIEMBRO));
        usuario2.setLocalizacion(new Localizacion());
        repoUsuario.agregar(usuario2);
    }

    @Order(12)
    @Test
    void agregarMembresia() throws Exception {
        Usuario usuarioAdmin = repoUsuario.buscar(1L);
        Comunidad comunidad = repoComunidad.buscar(1L);
        Membresia membresia = new Membresia(comunidad, usuarioAdmin, repoRol.buscarPorNombre(TipoRol.MIEMBRO));
        comunidad.agregarMembresia(membresia);
        usuarioAdmin.unirseAComunidad(membresia);
        repoComunidad.modificar(comunidad);

        List<Comunidad> comunidades = repoComunidad.buscarTodos();
        Usuario usuarioComunidad = repoUsuario.buscar(2L);

        for (Comunidad com : comunidades) {
            Membresia mem = new Membresia(com, usuarioComunidad, repoRol.buscarPorNombre(TipoRol.ADMINISTRADOR_COMUNIDAD));
            com.agregarMembresia(mem);
            usuarioComunidad.unirseAComunidad(mem);
            repoComunidad.modificar(com);
        }
    }



@Order(13)
    @Test
    void agregarIncidenteARevisar() throws InterruptedException {
        Thread.sleep(1000);
        Usuario usuario = repoUsuario.buscar(4L);
        PrestacionDeServicio banioMedrano1 = repoPrestacion.buscar(1L);

        Incidente incidente = new Incidente(usuario, "baño sucio", banioMedrano1, new Date());

        repoIncidentes.agregar(incidente);

        usuario.agregarRevisionDeIncidente(repoIncidentes.buscar(9L));
        repoUsuario.modificar(usuario);
    }

}

