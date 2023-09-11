package Modelado;

import comunidades.Comunidad;
import org.junit.Assert;
import org.junit.Test;

import rankings.criterios.ImpactoComunidades;

import java.util.List;
import static org.junit.Assert.assertEquals;

public class RankingComunidadesTest {

    @Test
    public void RankingComunidadesTests() {

        /*// Crear el Ranking y generar el Ranking
        DataRepositorios dataRepositorios = new DataRepositorios();
        dataRepositorios.generarDatos();
        ImpactoComunidades ranking = new ImpactoComunidades("Impacto de la comunidad");
        List<Comunidad> resultado = ranking.generarRanking(dataRepositorios.repoComunidades.getComunidades());

        // Verificar el orden de las comunidades en el Ranking
        Assert.assertEquals(dataRepositorios.repoComunidades.getComunidades().get(0), resultado.get(0));  // comunidad1 tiene mayor impacto (más miembros / menos incidentes)
        Assert.assertEquals(dataRepositorios.repoComunidades.getComunidades().get(1), resultado.get(1));
        Assert.assertEquals(dataRepositorios.repoComunidades.getComunidades().get(2), resultado.get(2));*/
    }

}
