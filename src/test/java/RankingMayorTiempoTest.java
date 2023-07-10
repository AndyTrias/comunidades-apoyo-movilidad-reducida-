import comunidades.incidentes.Incidente;
import comunidades.servicios.PrestacionDeServicio;
import comunidades.servicios.Servicio;
import comunidades.usuario.Email;
import comunidades.usuario.Usuario;
import entidades.Entidad;
import entidades.Establecimiento;
import localizacion.Localizacion;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import rankings.RankingMayorTiempo;

import java.util.ArrayList;
import java.util.List;

public class RankingMayorTiempoTest {

    @Test
    public void generarRanking_SortedByAverageTime() {
        // Create some sample entities with incidents
        Entidad entidad1 = new Entidad("Entidad 1");
        Entidad entidad2 = new Entidad("Entidad 2");
        Entidad entidad3 = new Entidad("Entidad 3");

        PrestacionDeServicio prestacion1 = new PrestacionDeServicio(new Servicio("Servicio 1"), "Prestacion 1");
        PrestacionDeServicio prestacion2 = new PrestacionDeServicio(new Servicio("Servicio 2"), "Prestacion 2");
        PrestacionDeServicio prestacion3 = new PrestacionDeServicio(new Servicio("Servicio 3"), "Prestacion 3");

        Usuario usuario1 = new Usuario("Manu", "Torrente", new Email());
        Usuario usuario2 = new Usuario("Andy", "Trias", new Email());
        Usuario usuario3 = new Usuario("Franco", "Pesce", new Email());

        Incidente incidente1 = new Incidente(usuario1, "Observaciones 1", prestacion1);
        incidente1.cerrar();
        Incidente incidente2 = new Incidente(usuario2, "Observaciones 2", prestacion2);
        incidente2.cerrar();
        Incidente incidente3 = new Incidente(usuario3, "Observaciones 3", prestacion3);

        Localizacion localizacion1 = Mockito.mock(Localizacion.class);

        entidad1.agregarEstablecimiento(new Establecimiento("Establecimiento 1", localizacion1));
        entidad1.getPrestacionesDeServicios().add(prestacion1);
        entidad2.agregarEstablecimiento(new Establecimiento("Establecimiento 2", localizacion1));
        entidad2.getPrestacionesDeServicios().add(prestacion2);
        entidad3.agregarEstablecimiento(new Establecimiento("Establecimiento 3", localizacion1));
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
