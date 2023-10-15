package models.repositorios;

import models.comunidades.Rol;

public class RepoRol extends RepoGenerico<Rol>{

    public static RepoRol INSTANCE = new RepoRol();

    public RepoRol() {
        super(Rol.class);
    }

    public Rol buscarPorNombre(String nombre){
        return (Rol) entityManager()
                .createQuery("from Rol where nombre = :nombre")
                .setParameter("nombre", nombre)
                .getSingleResult();
    }
}
