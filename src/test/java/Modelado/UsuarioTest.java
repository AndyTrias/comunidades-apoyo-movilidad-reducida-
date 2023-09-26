package Modelado;

import noServer.comunidades.Comunidad;
import noServer.comunidades.Membresia;
import noServer.comunidades.Rol;
import noServer.usuario.Usuario;
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
        Rol rol = new Rol("Rol de Prueba", new HashSet<>());
        comunidad.agregarRol(rol);

        usuario.unirseAComunidad(comunidad, rol);

        List<Membresia> membresias = usuario.getMembresias();
        Assert.assertEquals(1, membresias.size());

        Membresia membresia = membresias.get(0);
        Assert.assertEquals(comunidad, membresia.getComunidad());
        Assert.assertEquals(rol, membresia.getRol());
    }

    @Test(expected = Exception.class)
    public void testAbandonarComunidadNoPertenece() throws Exception {
        Comunidad otraComunidad = new Comunidad("Otra Comunidad");
        usuario.abandonarComunidad(otraComunidad);
    }

    @Test
    public void testAbandonarComunidad() throws Exception {
        Rol rol = new Rol("Rol de Prueba", new HashSet<>());
        comunidad.agregarRol(rol);
        usuario.unirseAComunidad(comunidad, rol);

        usuario.abandonarComunidad(comunidad);

        List<Membresia> membresias = usuario.getMembresias();
        Assert.assertEquals(0, membresias.size());
    }


    @Test
    public void testAgregarRol() {
        Rol rol = new Rol(" Rolde Prueba", new HashSet<>());
        comunidad.agregarRol(rol);

        List<Rol> roles = comunidad.getRoles();
        Assert.assertEquals(2, roles.size()); // El rol base más el nuevo rol
        Assert.assertTrue(roles.contains(rol));
    }

    @Test
    public void testEliminarRol() {
        Rol rol = new Rol("Rol de Prueba", new HashSet<>());
        comunidad.agregarRol(rol);

        comunidad.eliminarRol(rol);

        List<Rol> roles = comunidad.getRoles();
        Assert.assertEquals(1, roles.size()); // Solo el rol base
        }
}
