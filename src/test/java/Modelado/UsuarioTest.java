package Modelado;

import models.comunidades.Comunidad;
import models.comunidades.Membresia;
import models.comunidades.Rol;
import models.usuario.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

public class UsuarioTest {
    private Usuario usuario;
    private Comunidad comunidad;

    @Before
    public void setUp() {
        usuario = new Usuario("John", "Doe", "");
        comunidad = new Comunidad("Comunidad de Prueba");
    }


    @Test
    public void testUnirseAComunidad() {
        Membresia membresiaNueva = new Membresia(comunidad, usuario, new Rol());
        usuario.unirseAComunidad(membresiaNueva);

        List<Membresia> membresias = usuario.getMembresias();
        Assert.assertEquals(1, membresias.size());

        Membresia membresia = membresias.get(0);
        Assert.assertEquals(comunidad, membresia.getComunidad());
    }

    @Test(expected = Exception.class)
    public void testAbandonarComunidadNoPertenece() throws Exception {
        Comunidad otraComunidad = new Comunidad("Otra Comunidad");
        usuario.abandonarComunidad(otraComunidad);
    }

    @Test
    public void testAbandonarComunidad() throws Exception {
        Membresia membresiaNueva = new Membresia(comunidad, usuario, new Rol());
        usuario.unirseAComunidad(membresiaNueva);
        usuario.abandonarComunidad(comunidad);

        List<Membresia> membresias = usuario.getMembresias();
        Assert.assertEquals(0, membresias.size());
    }

}
