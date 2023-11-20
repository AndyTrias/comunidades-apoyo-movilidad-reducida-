package models.repositorios;

import models.entidades.EntidadPrestadora;

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
}
