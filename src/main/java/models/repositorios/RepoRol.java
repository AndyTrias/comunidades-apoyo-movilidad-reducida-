package models.repositorios;

import models.comunidades.Rol;
import models.comunidades.TipoRol;

public class RepoRol extends RepoGenerico<Rol>{

    public static RepoRol INSTANCE = new RepoRol();

    public RepoRol() {
        super(Rol.class);
    }

    public Rol buscarPorNombre(TipoRol tipoRol){
        return (Rol) entityManager()
                .createQuery("from Rol where tipoRol = :tipoRol")
                .setParameter("tipoRol", tipoRol)
                .getSingleResult();
    }
}
