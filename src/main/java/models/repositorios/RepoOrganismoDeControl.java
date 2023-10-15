package models.repositorios;

import models.entidades.OrganismoDeControl;

public class RepoOrganismoDeControl extends RepoGenerico<OrganismoDeControl>{

    public static RepoOrganismoDeControl INSTANCE = new RepoOrganismoDeControl();

    public RepoOrganismoDeControl() {
        super(OrganismoDeControl.class);
    }
}
