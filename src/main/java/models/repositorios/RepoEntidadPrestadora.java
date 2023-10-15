package models.repositorios;

import models.entidades.EntidadPrestadora;

public class RepoEntidadPrestadora extends RepoGenerico<EntidadPrestadora>{
    public static RepoEntidadPrestadora INSTANCE = new RepoEntidadPrestadora();

    public RepoEntidadPrestadora() {
        super(EntidadPrestadora.class);
    }
}
