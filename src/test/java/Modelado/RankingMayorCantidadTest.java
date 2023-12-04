package Modelado;

import models.entidades.Entidad;
import models.entidades.Establecimiento;
import models.incidentes.Incidente;
import models.localizacion.Localizacion;
import models.localizacion.UbicacionExacta;
import models.rankings.criterios.MayorCantidad;
import models.rankings.informes.Ranking;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import models.usuario.Usuario;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankingMayorCantidadTest {
    private Entidad entidad1;
    private Entidad entidad2;
    private Entidad entidad3;

    @Test
    public void elOrdenDelRankingEsCorrecto() {
        createEntitiesWithIncidents();

        MayorCantidad ranking = new MayorCantidad("Mayor cantidad de incidentes");
        List<Entidad> entidades = Arrays.asList(entidad1, entidad2, entidad3);
        List<Ranking> rankingList = ranking.generarRanking(entidades);

        assertEquals(entidad1, rankingList.get(0));
        assertEquals(entidad2, rankingList.get(1));
        assertEquals(entidad3, rankingList.get(2));
    }

    private void createEntitiesWithIncidents() {
        entidad1 = createEntityWithIncidents("Entidad 1", 10);
        entidad2 = createEntityWithIncidents("Entidad 2", 2);
        entidad3 = createEntityWithIncidents("Entidad 3", 3);
    }

    private Entidad createEntityWithIncidents(String name, int numEstablecimientos) {
        Entidad entidad = new Entidad(name, new Localizacion());

        for (int i = 0; i < numEstablecimientos; i++) {
            Establecimiento establecimiento = new Establecimiento("Establecimiento " + i, new Localizacion());
            entidad.agregarEstablecimiento(establecimiento);

            List<PrestacionDeServicio> prestaciones = createPrestaciones(i + 1);

            for (PrestacionDeServicio prestacion : prestaciones) {
                establecimiento.agregarServicioPrestado(prestacion);
            }

            createIncidents(prestaciones);
        }

        return entidad;
    }

    private List<PrestacionDeServicio> createPrestaciones(int num) {
        List<PrestacionDeServicio> prestaciones = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            prestaciones.add(new PrestacionDeServicio(new Servicio("Servicio " + i), "Prestacion " + i, new UbicacionExacta(i, i)));
        }
        return prestaciones;
    }

    private void createIncidents(List<PrestacionDeServicio> prestaciones) {
        for (PrestacionDeServicio prestacion : prestaciones) {
            Usuario usuario = new Usuario("Usuario", "Apellido", "");
            Incidente incidente = new Incidente(usuario, "Observaciones", prestacion, new Date());
            incidente.cerrar();
        }
    }

}

