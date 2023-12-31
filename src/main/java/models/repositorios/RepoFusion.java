package models.repositorios;

import models.comunidades.Fusion;

import java.util.List;
import java.util.Optional;

public class RepoFusion extends RepoGenerico<Fusion> {

    public static RepoFusion INSTANCE = new RepoFusion();

    public RepoFusion() {
        super(Fusion.class);
    }

    public Optional<Fusion> buscarPorComunidades(Fusion fusion) {
        return entityManager().createQuery("select f from Fusion f where f.comunidad1 = :comunidad1 and f.comunidad2 = :comunidad2", Fusion.class)
                .setParameter("comunidad1", fusion.getComunidad1())
                .setParameter("comunidad2", fusion.getComunidad2())
                .getResultStream()
                .findFirst();
    }

    public List<Fusion> buscarNoRealizadas() {
        return entityManager().createQuery("select f from Fusion f where f.realizada = false", Fusion.class)
                .getResultList();
    }
}
