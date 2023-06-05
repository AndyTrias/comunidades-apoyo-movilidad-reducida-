import org.junit.jupiter.api.Test;
import comunidades.Usuario;
import usuarios.Contrasenia.ValidadorDeContrasenia;
import usuarios.Usuario;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {
    @Test
    public void testSeLeSeteaLaContraseniaAlUsuario() throws Exception {
        Usuario usuario = new Usuario();
        String contrasenia = "ashjkdashjkdasdhjkaskhjafbjkawdbkj";

        usuario.setContrasenia(contrasenia);

        assertEquals(contrasenia, usuario.getContrasenia());
    }

    @Test
    public void testSeLeSeteaUnaContraseniaInseguraAlUsuario() throws Exception {
        Usuario usuario = new Usuario();
        String contrasenia = "password";

        assertThrows(Exception.class, () -> usuario.setContrasenia(contrasenia));
    }
}
