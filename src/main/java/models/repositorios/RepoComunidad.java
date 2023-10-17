package models.repositorios;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.comunidades.Comunidad;
import models.incidentes.IncidenteDeComunidad;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class RepoComunidad extends RepoGenerico<Comunidad>{

    public static final RepoComunidad INSTANCE = new RepoComunidad();

    public RepoComunidad() {
        super(Comunidad.class);
    }

    public IncidenteDeComunidad buscarIncidenteDeComunidad(Long idComunidad, Long idIncidente) {
        TypedQuery<IncidenteDeComunidad> query = entityManager().createQuery(
            "SELECT ic FROM Comunidad c " +
                "JOIN c.incidentes ic " +
                "WHERE ic.id = :incidenteId AND c.id = :comunidadId",
            IncidenteDeComunidad.class);

        query.setParameter("incidenteId", idIncidente);
        query.setParameter("comunidadId", idComunidad);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null when no matching IncidenteDeComunidad is found.
        }
    }

}







