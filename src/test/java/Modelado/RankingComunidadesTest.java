package Modelado;

import models.comunidades.Comunidad;
import models.localizacion.UbicacionExacta;
import models.rankings.criteriosNoUsados.ImpactoComunidades;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankingComunidadesTest {

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void RankingComunidadesTests() {
        List<Comunidad> comunidades = new ArrayList<>();
        Servicio servicio = new Servicio("baño hombres");
        PrestacionDeServicio banioMedrano = new PrestacionDeServicio(servicio, "baño Medrano", new UbicacionExacta(1, 1));
        PrestacionDeServicio banioCastroBarros= new PrestacionDeServicio(servicio, "baño Castro Barros", new UbicacionExacta(2, 2));

        // Creamos las 3 comunidades
        Comunidad comunidad1 = new Comunidad("comunidad1");
        comunidad1.agregarServicioDeInteres(banioMedrano);
        Comunidad comunidad2 = new Comunidad("comunidad2");
        comunidad2.agregarServicioDeInteres(banioMedrano);
        Comunidad comunidad3 = new Comunidad("comunidad3");
        comunidad3.agregarServicioDeInteres(banioMedrano);
        comunidad3.agregarServicioDeInteres(banioCastroBarros);

        comunidades.add(comunidad1);
        comunidades.add(comunidad2);
        comunidades.add(comunidad3);

        // Crear el Ranking y generar el Ranking
        ImpactoComunidades ranking = new ImpactoComunidades("Impacto de la comunidad");
        List<Comunidad> resultado = ranking.generarRanking(comunidades);

        // Verificar el orden de las comunidades en el Ranking
        assertEquals(comunidades.get(0), resultado.get(0));  // comunidad1 tiene mayor impacto (más miembros / menos incidentes)
        assertEquals(comunidades.get(1), resultado.get(1));
        assertEquals(comunidades.get(2), resultado.get(2));
    }

}
