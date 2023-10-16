package Modelado;

import models.usuario.Contrasenia.DiezMilPeoresContrasenias;
import models.usuario.Contrasenia.ValidadorDeContrasenia;
import models.usuario.Contrasenia.ValidarLongitud;
import models.configs.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import models.usuario.Usuario;

import static org.junit.jupiter.api.Assertions.*;

public class ContraseniaTest {

    ValidadorDeContrasenia validadorDeContrasenia;
    @BeforeEach
    public void setUp() throws Exception {
        this.validadorDeContrasenia = ServiceLocator.getValidadorCompleto();
    }

    @Test
    public void testSetContraseniaQueCumpleTodo() throws Exception {
        Usuario usuario = new Usuario("ejemplo", "ejemplo", "");
        String contrasenia = "contraseñasupersegura1234";

        usuario.setContrasenia(contrasenia);

        assertEquals(contrasenia, usuario.getContrasenia());
    }

    @Test
    public void testSeLeSeteaUnaContraseniaInseguraAlUsuario() throws Exception {
        Usuario usuario = new Usuario("ejemplo", "ejemplo", "");
        String contrasenia = "password";

        assertThrows(Exception.class, () -> usuario.setContrasenia(contrasenia));
    }
}
