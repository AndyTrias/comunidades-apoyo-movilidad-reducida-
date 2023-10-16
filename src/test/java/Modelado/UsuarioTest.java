package Modelado;

import models.comunidades.Comunidad;
import models.comunidades.Membresia;
import models.comunidades.Rol;
import models.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

public class UsuarioTest {
    private Usuario usuario;
    private Comunidad comunidad;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario("John", "Doe", "");
        comunidad = new Comunidad("Comunidad de Prueba");
    }

    @Test
    public void testUnirseAComunidad() {
        Membresia membresiaNueva = new Membresia(comunidad, usuario, new Rol());
        usuario.unirseAComunidad(membresiaNueva);

        List<Membresia> membresias = usuario.getMembresias();
        assertEquals(1, membresias.size());

        Membresia membresia = membresias.get(0);
        assertEquals(comunidad, membresia.getComunidad());
    }

    @Test
    public void testAbandonarComunidadNoPertenece() {
        Comunidad otraComunidad = new Comunidad("Otra Comunidad");
        assertThrows(Exception.class, () -> usuario.abandonarComunidad(otraComunidad));
    }

    @Test
    public void testAbandonarComunidad() throws Exception {
        Membresia membresiaNueva = new Membresia(comunidad, usuario, new Rol());
        usuario.unirseAComunidad(membresiaNueva);
        usuario.abandonarComunidad(comunidad);

        List<Membresia> membresias = usuario.getMembresias();
        assertEquals(0, membresias.size());
    }
}

