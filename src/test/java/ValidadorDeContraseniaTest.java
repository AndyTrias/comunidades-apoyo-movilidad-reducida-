import org.junit.Test;
import usuarios.Contrasenia.ValidadorDeContrasenia;

import static org.junit.Assert.*;

public class ValidadorDeContraseniaTest {

    @Test
    public void testContraseniaValida() {
        String contrasenia = "MiContrasenia123";
        assertTrue(ValidadorDeContrasenia.validarContrasenia(contrasenia));
    }
    @Test
    public void testContraseniaMenosDe8Caracteres() {
        String contrasenia = "abc123";
        assertFalse(ValidadorDeContrasenia.validarContrasenia(contrasenia));
    }

    @Test
    public void testValidarContraseniaMasDe63Caracteres() {
        String contrasenia = "EstaContraseniaTieneMasDeSesentaYCincoCaracteres1234asdhkiasjhdaskjdahskjdsahdkasjdhaskjdahsdkjashdaskjdahdks";
        assertFalse(ValidadorDeContrasenia.validarContrasenia(contrasenia));
    }

    @Test
    public void testValidarContraseniaInsegura() {
        String contrasenia = "password";
        assertFalse(ValidadorDeContrasenia.validarContrasenia(contrasenia));
    }

    @Test
    public void testValidarContraseniaVacia() {
        String contrasenia = "";
        assertFalse(ValidadorDeContrasenia.validarContrasenia(contrasenia));
    }






}