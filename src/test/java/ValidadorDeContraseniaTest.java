import org.junit.Test;
import usuarios.Contrasenia.ValidadorDeContrasenia;

import static org.junit.Assert.*;

public class ValidadorDeContraseniaTest {

    @Test
    public void testValidarContrasenia() {

        // Contrasenia válida
        String contraseniaValida = "MiContrasenia123";
        assertTrue(ValidadorDeContrasenia.validarContrasenia(contraseniaValida));

        // Contrasenia con menos de 8 caracteres
        String contraseniaCorta = "abc123";
        assertFalse(ValidadorDeContrasenia.validarContrasenia(contraseniaCorta));

        // Contrasenia con más de 64 caracteres
        String contraseniaLarga = "EstaContraseniaTieneMasDeSesentaYCuatroCaracteresYDeberiaSerInvalida123";
        assertFalse(ValidadorDeContrasenia.validarContrasenia(contraseniaLarga));

        // Contrasenia que se encuentra en la lista de contraseñas comunes
        String contraseniaComun = "12345678";
        assertFalse(ValidadorDeContrasenia.validarContrasenia(contraseniaComun));

        // Contrasenia que cumple algunas condiciones pero no todas
        String contraseniaNoValida = "abcd123";
        assertFalse(ValidadorDeContrasenia.validarContrasenia(contraseniaNoValida));
    }
}