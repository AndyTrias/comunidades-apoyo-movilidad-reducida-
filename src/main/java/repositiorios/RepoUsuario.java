package repositiorios;

import usuario.Usuario;
import lombok.Getter;

import java.util.List;

public class RepoUsuario extends RepoGenerico<Usuario> {
    public RepoUsuario() {
        super(Usuario.class);
    }

    public List<Usuario> buscarPorNombre(String nombre) {
        return entityManager().createQuery("select e from Usuario e where e.nombre = :nombre", Usuario.class)
                .setParameter("nombre", nombre)
                .getResultList();
    }
}

