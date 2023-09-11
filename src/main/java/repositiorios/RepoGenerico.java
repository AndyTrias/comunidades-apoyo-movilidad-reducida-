package repositiorios;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public abstract class RepoGenerico<T> implements WithSimplePersistenceUnit {
    private final EntityTransaction transaction = entityManager().getTransaction();

    protected abstract Class<T> getEntityClass();

    public void agregar(T entidad) {
        persist(entidad);
        transaction.commit();
    }

    public void modificar(T entidad) {
        merge(entidad);
        transaction.commit();
    }

    public void eliminar(T entidad) {
        remove(entidad);
        transaction.commit();
    }

    public T buscar(Long id) {
        return find(getEntityClass(), id);
    }

    public List<T> buscarTodos() {
        return entityManager().createQuery("select e from " + getEntityClass().getSimpleName() + " e", getEntityClass()).getResultList();
    }
}
