package models.repositorios;


import models.localizacion.Localizacion;


public class RepoLocalizacion extends RepoGenerico<Localizacion>{

    public static RepoLocalizacion INSTANCE = new RepoLocalizacion();

    public RepoLocalizacion() {
        super(Localizacion.class);
    }


}