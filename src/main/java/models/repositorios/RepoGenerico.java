package models.repositorios;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.util.List;

public abstract class RepoGenerico<T> implements WithSimplePersistenceUnit {
    private final Class<T> entityClass;
    private final EntityManager em = entityManager();

    protected RepoGenerico(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void agregar(T entidad) {
        em.getTransaction().begin();
        em.persist(entidad);
        em.getTransaction().commit();
    }

    public void modificar(T entidad) {
        em.getTransaction().begin();
        em.merge(entidad);
        em.getTransaction().commit();
    }

    public void eliminar(T entidad) {
        em.getTransaction().begin();
        em.remove(entidad);
        em.getTransaction().commit();
    }

    public T buscar(Long id) {
        entityManager().clear();
        return em.find(entityClass, id);
    }

    public List<T> buscarTodos() {
        entityManager().clear();
        return em
                .createQuery("select e from " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }
}
