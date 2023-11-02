package models.repositorios;

import models.entidades.OrganismoDeControl;

public class RepoOrganismoDeControl extends RepoGenerico<OrganismoDeControl>{

    public static RepoOrganismoDeControl INSTANCE = new RepoOrganismoDeControl();

    public RepoOrganismoDeControl() {
        super(OrganismoDeControl.class);
    }

    public OrganismoDeControl buscarPorUsarioDesignado(Long idUsuario){
        return entityManager().createQuery("SELECT oc FROM OrganismoDeControl oc WHERE oc.personaDesignada.id = :idUsuario", OrganismoDeControl.class)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();
    }
}
