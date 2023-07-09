import comunidades.Comunidad;
import comunidades.Permiso;
import comunidades.Rol;
import comunidades.incidentes.Incidente;
import comunidades.servicios.PrestacionDeServicio;
import comunidades.servicios.Servicio;
import comunidades.usuario.Email;
import comunidades.usuario.Usuario;
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

    @BeforeEach
    public void setUp() throws Exception {
        this.comunidad1 = new Comunidad("comunidad1");
        Set<Permiso> permisos = new HashSet<>();
        Permiso enviarMensajes = new Permiso();
        permisos.add(enviarMensajes);
        Rol rol = new Rol("rol1", permisos);
        comunidad1.agregarRol(rol);

        Email email = new Email();
        email.nombreDeUsuario = "francopescee";
        email.dominio = "gmail.com";
        this.franco = new Usuario("franco", "pesce", email);

        this.juan = new Usuario("juan", "perez", new Email());
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
}
