package repositiorios;

import comunidades.Comunidad;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class RepoComunidades extends RepoGenerico<Comunidad>{

    protected RepoComunidades(Class<Comunidad> entityClass) {
        super(entityClass);
    }
}
