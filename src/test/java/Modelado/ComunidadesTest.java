package Modelado;

import noServer.comunidades.Comunidad;
import noServer.comunidades.Permiso;
import noServer.comunidades.Rol;
import noServer.localizacion.UbicacionExacta;
import noServer.servicios.PrestacionDeServicio;
import noServer.servicios.Servicio;
import noServer.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComunidadesTest {
    private Comunidad comunidad1;
    private Usuario franco;
    private Usuario juan;

    private Servicio servicio;

    private PrestacionDeServicio banioMedrano;

    private PrestacionDeServicio banioCastroBarros;

    private Comunidad comunidad2;

    private Comunidad comunidad3;


    @BeforeEach
    public void setUp() throws Exception {

        // Creamos la prestacion de prestacion de servicio
        servicio = new Servicio("baño hombres");
        banioMedrano = new PrestacionDeServicio(servicio, "baño Medrano", new UbicacionExacta(1, 1));
        banioCastroBarros= new PrestacionDeServicio(servicio, "baño Castro Barros", new UbicacionExacta(2, 2));

        // Creamos las 3 comunidades
        comunidad1 = new Comunidad("comunidad1");
        comunidad1.agregarServicioDeInteres(banioMedrano);
        comunidad2 = new Comunidad("comunidad2");
        comunidad2.agregarServicioDeInteres(banioMedrano);
        comunidad3 = new Comunidad("comunidad3");
        comunidad3.agregarServicioDeInteres(banioMedrano);
        comunidad3.agregarServicioDeInteres(banioCastroBarros);


        Set<Permiso> permisos = new HashSet<>();
        Permiso enviarMensajes = new Permiso();
        permisos.add(enviarMensajes);
        Rol rol = new Rol("rol1", permisos);
        comunidad1.agregarRol(rol);

        this.franco = new Usuario("franco", "pesce", "francopescee@gmail.com");

        this.juan = new Usuario("juan", "perez", "");
    }

    @Test
    public void testAgregarUsuarioAComunidadVacia() {
        Rol rolDelUsuario = comunidad1.aceptarUsuario(franco);
        franco.unirseAComunidad(comunidad1, rolDelUsuario);

        assertEquals(franco.getMembresias().size(), 1);
        assertEquals(comunidad1.getCantidadDeUsuarios(), 1);
    }

    @Test
    public void cambiarRolDeUsuarioARolQueNoExiste() throws Exception {
        Rol rolDelUsuario = comunidad1.aceptarUsuario(franco);
        franco.unirseAComunidad(comunidad1, rolDelUsuario);
        Rol nuevoRol = new Rol("rol2", new HashSet<>());

        assertThrows(Exception.class, () -> comunidad1.cambiarRol(franco, nuevoRol));
    }

    @Test
    public void cambiarRolDeUsuario() throws Exception {
        Rol rolDelUsuario = comunidad1.aceptarUsuario(franco);
        franco.unirseAComunidad(comunidad1, rolDelUsuario);
        Rol nuevoRol = new Rol("rol2", new HashSet<>());
        comunidad1.agregarRol(nuevoRol);
        comunidad1.cambiarRol(franco, nuevoRol);

        assertEquals(nuevoRol.getUsuarios().size(), 1);
    }

    @Test
    public void eliminarUsuarioDeComunidad() throws Exception {
        Rol rolDelUsuario = comunidad1.aceptarUsuario(franco);
        franco.unirseAComunidad(comunidad1, rolDelUsuario);
        comunidad1.eliminarUsuario(franco);
        franco.abandonarComunidad(comunidad1);

        assertEquals(comunidad1.getCantidadDeUsuarios(), 0);
        assertEquals(franco.getMembresias().size(), 0);
    }

    @Test
    public void testCantidadDeAfectados(){
        franco.unirseAComunidad(comunidad1, comunidad1.aceptarUsuario(franco));
       franco.getMembresia(comunidad1).cambiarAfectacion(banioMedrano, true);
       assertEquals(comunidad1.getCantidadDeAfectados(), 1);

    }
}
