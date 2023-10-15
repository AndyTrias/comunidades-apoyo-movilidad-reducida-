package models.repositorios;

import models.comunidades.Membresia;
import models.entidades.OrganismoDeControl;

public class RepoMembresia extends RepoGenerico<Membresia> {

    public static RepoMembresia INSTANCE = new RepoMembresia();

    public RepoMembresia() {
        super(Membresia.class);
    }
}
