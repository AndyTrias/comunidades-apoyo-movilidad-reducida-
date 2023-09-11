import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositiorios.RepoUsuario;
import usuario.Interes;
import usuario.Usuario;

import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrmTest implements SimplePersistenceTest {

    private RepoUsuario repoUsuario;

    @BeforeEach
    void setUp() {
        repoUsuario = new RepoUsuario();
    }

    @Test
    void contextUp() {
        assertNotNull(entityManager());
    }

    @Test
    void contextUpWithTransaction() throws Exception {
        EntityTransaction transaction = entityManager().getTransaction();
        transaction.commit();
        transaction.begin();

        Usuario franco = new Usuario("franco", "pesce", "");
        entityManager().persist(franco);

        transaction.commit();

        Usuario francoRecuperado = entityManager().createQuery("select u from Usuario u", Usuario.class).getSingleResult();
        assertEquals(franco, francoRecuperado);
    }

    @Test
    void obtenerUsuarioId1(){
        Usuario recuperado = repoUsuario.buscar(1L);
        assertEquals("franco", recuperado.getNombre());
    }

    @Test
    void agregarUsuario() {
        Usuario usuario = new Usuario("Juan", "Perez", "");
        repoUsuario.agregar(usuario);
    }

    @Test
    void modificarUsuario() {
        Usuario usuario = repoUsuario.buscar(11L);
        usuario.setCorreoElectronico("francopescee@gmail.com");
        repoUsuario.modificar(usuario);

        Usuario usuarioModificado = repoUsuario.buscar(11L);
        assertEquals("francopescee@gmail.com", usuarioModificado.getCorreoElectronico());
    }

    @Test
    void eliminarUsuario() {
        int cantidadUsuarios = repoUsuario.buscarTodos().size();
        Usuario usuario = repoUsuario.buscar(11L);
        repoUsuario.eliminar(usuario);

        assertEquals(cantidadUsuarios - 1, repoUsuario.buscarTodos().size());
    }

    @Test
    void obtenerUsuarioPorNombre() {
        Usuario usuario = repoUsuario.buscarPorNombre("juan").get(0);
        assertEquals("Juan", usuario.getNombre());
    }
}
