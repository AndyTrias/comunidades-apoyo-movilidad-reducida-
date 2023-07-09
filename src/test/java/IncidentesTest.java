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

public class IncidentesTest {
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

        this.franco = new Usuario("franco", "pesce", new Email());
        this.juan = new Usuario("juan", "perez", new Email());

        Rol rolDeComunidad = comunidad1.aceptarUsuario(juan);
        juan.unirseAComunidad(comunidad1, rolDeComunidad);
        rolDeComunidad = comunidad1.aceptarUsuario(franco);
        franco.unirseAComunidad(comunidad1, rolDeComunidad);

    }

    @Test
    public void testAperturaDeIncidente(){

        Servicio servicio = new Servicio("baño hombres");
        PrestacionDeServicio prestacionDeServicio = new PrestacionDeServicio(servicio);

        // abrir o crear incidente generico
        Incidente incidente = new Incidente(franco, "observaciones", prestacionDeServicio);
        // crear incidente en cada una de las comunidades del usuario
        franco.getComunidades().forEach(c ->c.abrirIncidente(incidente));

        assertEquals(comunidad1.getIncidentesAbiertos().size(), 1);
        assertEquals(prestacionDeServicio.getIncidentes().size(), 1);
        assertEquals(juan.getComunidades().get(0).getIncidentesAbiertos().size(), 1);
    }

    @Test
    public void testCerrarIncidente(){
        Servicio servicio = new Servicio("baño mujeres");
        PrestacionDeServicio prestacionDeServicio = new PrestacionDeServicio(servicio);

        // abrir o crear incidente generico
        Incidente incidente = new Incidente(franco, "observaciones", prestacionDeServicio);
        // crear incidente en cada una de las comunidades del usuario
        franco.getComunidades().forEach(c ->c.abrirIncidente(incidente));

        // cerrar incidente
        juan.getComunidades().get(0).cerrarIncidente(incidente);

        assertEquals(comunidad1.getIncidentesCerrados().size(), 1);
        assertEquals(prestacionDeServicio.getIncidentes().size(), 1);
    }
}
