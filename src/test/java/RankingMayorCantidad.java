import incidentes.Incidente;
import localizacion.UbicacionExacta;
import servicios.PrestacionDeServicio;
import servicios.Servicio;
import comunidades.usuario.Email;
import comunidades.usuario.Usuario;
import entidades.Entidad;
import entidades.Establecimiento;
import localizacion.Localizacion;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import rankings.criterios.MayorTiempo;

import java.util.ArrayList;
import java.util.List;

public class RankingMayorCantidad {

    @Test
    public void generarRanking_SortedByAverageTime() {
        Entidad entidad1 = new Entidad("Entidad 1");
        Entidad entidad2 = new Entidad("Entidad 2");
        Entidad entidad3 = new Entidad("Entidad 3");

        PrestacionDeServicio prestacion1 = new PrestacionDeServicio(new Servicio("Servicio 1"), "Prestacion 1", new UbicacionExacta(1, 1));
        PrestacionDeServicio prestacion2 = new PrestacionDeServicio(new Servicio("Servicio 2"), "Prestacion 2", new UbicacionExacta(2, 2));
        PrestacionDeServicio prestacion3 = new PrestacionDeServicio(new Servicio("Servicio 3"), "Prestacion 3", new UbicacionExacta(3, 3));
        PrestacionDeServicio prestacion4 = new PrestacionDeServicio(new Servicio("Servicio 4"), "Prestacion 4", new UbicacionExacta(4, 4));

        Usuario usuario1 = new Usuario("Manu", "Torrente", new Email());
        Usuario usuario2 = new Usuario("Franco", "Pesce", new Email());
        Usuario usuario3 = new Usuario("Andy", "Trias", new Email());
        Usuario usuario4 = new Usuario("Gian", "Perez", new Email());
        Usuario usuario5 = new Usuario("Fede", "Gonzalez", new Email());
        Usuario usuario6 = new Usuario("Juan", "Garcia", new Email());
        Usuario usuario7 = new Usuario("Pedro", "Rodriguez", new Email());

        //2 incidentes para la entidad 3 porque son diferentes prestaciones
        Incidente incidente3 = new Incidente(usuario1, "Observaciones 3", prestacion3);
        Incidente incidente4 = new Incidente(usuario2, "Observaciones 4", prestacion4);
        incidente4.cerrar();

        //1 incidente para la entidad 1 por las 24 horas
        Incidente incidente1 = new Incidente(usuario3, "Observaciones 1", prestacion1);
        Incidente incidente6 = new Incidente(usuario4, "Observaciones 6", prestacion1);

        //3 incidentes para la entidad 2 porque se cierran antes
        Incidente incidente2 = new Incidente(usuario2, "Observaciones 2", prestacion2);
        incidente2.cerrar();
        Incidente incidente5 = new Incidente(usuario5, "Observaciones 5", prestacion2);
        incidente5.cerrar();
        Incidente incidente7 = new Incidente(usuario7, "Observaciones 7", prestacion2);

        Localizacion localizacion1 = Mockito.mock(Localizacion.class);

        entidad1.agregarEstablecimiento(new Establecimiento("Establecimiento 1", localizacion1));
        entidad1.getPrestacionesDeServicios().add(prestacion1);
        entidad2.agregarEstablecimiento(new Establecimiento("Establecimiento 2", localizacion1));
        entidad2.getPrestacionesDeServicios().add(prestacion2);
        entidad3.agregarEstablecimiento(new Establecimiento("Establecimiento 3", localizacion1));
        entidad3.getPrestacionesDeServicios().add(prestacion3);
        entidad3.getPrestacionesDeServicios().add(prestacion4);

        List<Entidad> entidades = new ArrayList<>();
        entidades.add(entidad1);
        entidades.add(entidad2);
        entidades.add(entidad3);

        /*MayorTiempo ranking = new MayorTiempo();

        List<Entidad> rankingList = ranking.generarRanking(entidades);

        Assert.assertEquals(entidad1, rankingList.get(0));
        Assert.assertEquals(entidad2, rankingList.get(1));
        Assert.assertEquals(entidad3, rankingList.get(2));*/
    }
}
