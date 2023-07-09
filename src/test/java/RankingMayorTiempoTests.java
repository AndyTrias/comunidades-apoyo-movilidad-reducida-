import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert;
import static org.mockito.Mockito.*;

public class RankingMayorTiempoTest {

    @Test
    public void generarRanking_SortedByAverageTime() {
        // Create some sample entities with incidents
        Entidad entidad1 = new Entidad("Entidad 1");
        Entidad entidad2 = new Entidad("Entidad 2");
        Entidad entidad3 = new Entidad("Entidad 3");

        PrestacionDeServicio prestacion1 = new PrestacionDeServicio(new Servicio("Servicio 1"));
        PrestacionDeServicio prestacion2 = new PrestacionDeServicio(new Servicio("Servicio 2"));
        PrestacionDeServicio prestacion3 = new PrestacionDeServicio(new Servicio("Servicio 3"));

        Incidente incidente1 = new Incidente(new Usuario("Usuario 1"), "Observaciones 1", prestacion1);
        incidente1.cerrar();
        Incidente incidente2 = new Incidente(new Usuario("Usuario 2"), "Observaciones 2", prestacion2);
        incidente2.cerrar();
        Incidente incidente3 = new Incidente(new Usuario("Usuario 3"), "Observaciones 3", prestacion3);

        entidad1.agregarEstablecimiento(new Establecimiento("Establecimiento 1"));
        entidad1.getPrestacionesDeServicios().add(prestacion1);
        entidad2.agregarEstablecimiento(new Establecimiento("Establecimiento 2"));
        entidad2.getPrestacionesDeServicios().add(prestacion2);
        entidad3.agregarEstablecimiento(new Establecimiento("Establecimiento 3"));
        entidad3.getPrestacionesDeServicios().add(prestacion3);

        List<Entidad> entidades = new ArrayList<>();
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
