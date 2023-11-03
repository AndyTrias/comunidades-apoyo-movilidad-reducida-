package models.repositorios;

import java.util.Date;
import java.util.List;
import models.rankings.informes.Informe;

public class RepoInformes extends RepoGenerico<Informe>{
    public static RepoInformes INSTANCE = new RepoInformes();

    public RepoInformes() {super(Informe.class);}

    public List<Informe> buscarPorFecha(Date fecha) {
        return entityManager().createQuery("from Informe where fecha = :fecha", Informe.class)
                .setParameter("fecha", fecha)
                .getResultList();
    }
}

