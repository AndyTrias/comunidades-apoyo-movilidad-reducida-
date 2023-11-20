package models.repositorios;

import models.entidades.Entidad;
import models.servicios.PrestacionDeServicio;

import java.util.List;

public class RepoEntidad extends RepoGenerico<Entidad> {
    public static RepoEntidad INSTANCE = new RepoEntidad();

    public RepoEntidad() {
        super(Entidad.class);
    }

    public List<Entidad> getEntidadesConPrestacion(PrestacionDeServicio prestacion){
        return entityManager().createQuery("select e from Entidad e join e.establecimientos es join es.servicios p where p = :prestacion", Entidad.class)
                .setParameter("prestacion", prestacion)
                .getResultList();
    }
}
