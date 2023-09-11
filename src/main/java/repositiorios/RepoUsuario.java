package repositiorios;

import lombok.Getter;
import usuario.Usuario;

public class RepoUsuario extends RepoGenerico<Usuario> {
    @Getter
    private static final RepoUsuario INSTANCE = new RepoUsuario();

    @Override
    protected Class<Usuario> getEntityClass() {
        return Usuario.class;
    }
}
