package repositiorios;

import lombok.Getter;
import lombok.Setter;
import servicios.PrestacionDeServicio;
import entidades.Entidad;

import java.util.ArrayList;
import java.util.List;

public class RepoEntidades extends RepoGenerico<Entidad> {

    protected RepoEntidades(Class<Entidad> entityClass) {
        super(entityClass);
    }
}
