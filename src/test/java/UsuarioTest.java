import org.junit.jupiter.api.Test;
import usuarios.Contrasenia.ValidadorDeContrasenia;
import usuarios.Usuario;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {
    @Test
    public void testVerificarContrasenia() throws Exception {
        Usuario usuario = new Usuario("JuanP2");
        String contrasenia = "ashjkdashjkdasdhjkaskhjafbjkawdbkj";

        usuario.setContrasenia(contrasenia);

        assertEquals(contrasenia, usuario.getContrasena());
    }

    @Test
    public void testContraseniaSegura() throws Exception {
        Usuario usuario = new Usuario("JuanP2");

        usuario.setContrasenia("ñplokijugfv6h");

        assertEquals("ñplokijugfv6h", usuario.getContrasena());
    }
}
