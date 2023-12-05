package models.repositorios;

import models.entidades.Entidad;
import models.entidades.EntidadPrestadora;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class RepoEntidadPrestadora extends RepoGenerico<EntidadPrestadora>{
    public static RepoEntidadPrestadora INSTANCE = new RepoEntidadPrestadora();

    public RepoEntidadPrestadora() {
        super(EntidadPrestadora.class);
    }

    public EntidadPrestadora buscarporUsuarioDesignado(Long idUsuario){
        return entityManager().createQuery("SELECT ep FROM EntidadPrestadora ep WHERE ep.personaDesignada.id = :idUsuario", EntidadPrestadora.class)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();
    }

    public EntidadPrestadora buscarPrestadoraporEntidad(Long idEntidad) {
        TypedQuery<EntidadPrestadora> query = entityManager().createQuery(
            "SELECT ep FROM EntidadPrestadora ep " +
                "JOIN ep.entidades e " +
                "WHERE e.id = :entidadId",
            EntidadPrestadora.class);

        query.setParameter("entidadId", idEntidad);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null when no matching EntidadPrestadora is found.
        }
    }
}
