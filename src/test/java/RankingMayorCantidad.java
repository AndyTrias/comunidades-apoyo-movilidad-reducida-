import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert;
import static org.mockito.Mockito.*;

public class RankingMayorCantidad {

    @Test
    public void generarRanking_SortedByAverageTime() {
        // Create some sample entities with incidents
        Entidad entidad1 = new Entidad("Entidad 1");
        Entidad entidad2 = new Entidad("Entidad 2");
        Entidad entidad3 = new Entidad("Entidad 3");

        PrestacionDeServicio prestacion1 = new PrestacionDeServicio(new Servicio("Servicio 1"));
        PrestacionDeServicio prestacion2 = new PrestacionDeServicio(new Servicio("Servicio 2"));
        PrestacionDeServicio prestacion3 = new PrestacionDeServicio(new Servicio("Servicio 3"));
        PrestacionDeServicio prestacion4 = new PrestacionDeServicio(new Servicio("Servicio 4"));

        //2 incidentes para la entidad 3 porque son diferentes prestaciones
        Incidente incidente3 = new Incidente(new Usuario("Usuario 3"), "Observaciones 3", prestacion3);
        Incidente incidente4 = new Incidente(new Usuario("Usuario 4"), "Observaciones 4", prestacion4);
        incidente4.cerrar();

        //1 incidente para la entidad 1 por las 24 horas
        Incidente incidente1 = new Incidente(new Usuario("Usuario 1"), "Observaciones 1", prestacion1);
        Incidente incidente6 = new Incidente(new Usuario("Usuario 6"), "Observaciones 6", prestacion1);

        //3 incidentes para la entidad 2 porque se cierran antes
        Incidente incidente2 = new Incidente(new Usuario("Usuario 2"), "Observaciones 2", prestacion2);
        incidente2.cerrar();
        Incidente incidente5 = new Incidente(new Usuario("Usuario 5"), "Observaciones 5", prestacion2);
        incidente5.cerrar();
        Incidente incidente7 = new Incidente(new Usuario("Usuario 7"), "Observaciones 7", prestacion2);

        entidad1.agregarEstablecimiento(new Establecimiento("Establecimiento 1"));
        entidad1.getPrestacionesDeServicios().add(prestacion1);
        entidad2.agregarEstablecimiento(new Establecimiento("Establecimiento 2"));
        entidad2.getPrestacionesDeServicios().add(prestacion2);
        entidad3.agregarEstablecimiento(new Establecimiento("Establecimiento 3"));
        entidad3.getPrestacionesDeServicios().add(prestacion3);
        entidad3.getPrestacionesDeServicios().add(prestacion4);

        List<Entidad> entidades = new ArrayList<>(Entidad);
        entidades.add(entidad1);
        entidades.add(entidad2);
        entidades.add(entidad3);

        // Create the ranking instance
        RankingMayorTiempo ranking = new RankingMayorTiempo();

        // Generate the ranking
        List<Entidad> rankingList = ranking.generarRanking(entidades);

        // Validate the ranking order based on average time
        Assert.assertEquals(entidad1, rankingList.get(0));
        Assert.assertEquals(entidad2, rankingList.get(1));
        Assert.assertEquals(entidad3, rankingList.get(2));
    }
}
