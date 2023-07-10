import comunidades.Comunidad;
import org.junit.Assert;
import org.junit.Test;
import rankings.RankingImpacto;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RankingComunidadesTest {

    @Test
    public void RankingComunidadesTests() {
        // Crear objetos de prueba
        Comunidad comunidad1 = mock(Comunidad.class);
        Comunidad comunidad2 = mock(Comunidad.class);
        Comunidad comunidad3 = mock(Comunidad.class);

        when(comunidad1.getCantidadDeUsuarios()).thenReturn(50);
        when(comunidad2.getCantidadDeUsuarios()).thenReturn(30);
        when(comunidad3.getCantidadDeUsuarios()).thenReturn(20);

        List<Comunidad> comunidades = new ArrayList<>();
        comunidades.add(comunidad1);
        comunidades.add(comunidad2);
        comunidades.add(comunidad3);

        // Crear el ranking y generar el ranking
        RankingImpacto ranking = new RankingImpacto();
        List<Comunidad> resultado = ranking.generarRanking(comunidades);

        // Verificar el orden de las comunidades en el ranking
        Assert.assertEquals(comunidad1, resultado.get(0));  // comunidad1 tiene mayor impacto (más miembros / menos incidentes)
        Assert.assertEquals(comunidad2, resultado.get(1));
        Assert.assertEquals(comunidad3, resultado.get(2));
    }

}
