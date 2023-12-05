package models.repositorios;

import models.entidades.Entidad;
import models.entidades.Establecimiento;
import models.servicios.PrestacionDeServicio;

import java.util.List;

public class RepoEntidad extends RepoGenerico<Entidad> {
    public static RepoEntidad INSTANCE = new RepoEntidad();

    public RepoEntidad() {
        super(Entidad.class);
    }

    public Entidad buscarEntidadporEstablecimiento(Establecimiento establecimiento) {
        return entityManager().createQuery(
                "SELECT e FROM Entidad e " +
                    "JOIN e.establecimientos es " +
                    "WHERE es.id = :establecimientoId", Entidad.class)
            .setParameter("establecimientoId", establecimiento.getId())
            .getSingleResult();
    }

}
