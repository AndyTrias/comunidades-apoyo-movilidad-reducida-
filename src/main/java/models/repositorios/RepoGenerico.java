package models.repositorios;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.util.List;

public abstract class RepoGenerico<T> implements WithSimplePersistenceUnit {
    private final Class<T> entityClass;

    protected RepoGenerico(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void agregar(T entidad) {
        beginTransaction();
        persist(entidad);
        commitTransaction();
    }

    public void modificar(T entidad) {
        beginTransaction();
        merge(entidad);
        commitTransaction();
    }

    public void eliminar(T entidad) {
        beginTransaction();
        remove(entidad);
        commitTransaction();
    }

    public T buscar(Long id) {
        return find(entityClass, id);
    }

    public List<T> buscarTodos() {
        return entityManager()
                .createQuery("select e from " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();


    }


}
