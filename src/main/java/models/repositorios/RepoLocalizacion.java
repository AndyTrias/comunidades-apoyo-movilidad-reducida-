package models.repositorios;


import models.localizacion.Localizacion;


public class RepoLocalizacion extends RepoGenerico<Localizacion>{

    public static RepoLocalizacion INSTANCE = new RepoLocalizacion();

    public RepoLocalizacion() {
        super(Localizacion.class);
    }

    public void agregarOModificar(Localizacion localizacion) {
        if (localizacion.getId() == 0) {
            this.agregar(localizacion);
        } else {
            this.modificar(localizacion);
        }
    }
}