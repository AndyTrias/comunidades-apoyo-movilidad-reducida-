package Modelado;

import usuario.Contrasenia.DiezMilPeoresContrasenias;
import usuario.Contrasenia.ValidadorDeContrasenia;
import usuario.Contrasenia.ValidarLongitud;
import configs.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usuario.Usuario;

import static org.junit.jupiter.api.Assertions.*;

public class ContraseniaTest {

    ValidadorDeContrasenia validadorDeContrasenia;
    @BeforeEach
    public void setUp() throws Exception {
        this.validadorDeContrasenia = ServiceLocator.getValidador();
        this.validadorDeContrasenia.activarValidador(new ValidarLongitud(), new DiezMilPeoresContrasenias());
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