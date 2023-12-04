package Modelado;

import models.entidades.Entidad;
import models.entidades.Establecimiento;
import models.incidentes.Incidente;
import models.localizacion.Localizacion;
import models.localizacion.UbicacionExacta;
import models.rankings.criterios.MayorTiempo;
import models.rankings.informes.Ranking;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import models.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankingMayorTiempoTest {
    private List<Entidad> entidades;

    @BeforeEach
    public void setUp() {
        entidades = createEntitiesWithIncidents(3);
    }

    @Test
    public void elOrdenDelRankingEsCorrecto() {
        MayorTiempo ranking = new MayorTiempo("Mayor Tiempo de resolucion");
        List<Ranking> rankingList = ranking.generarRanking(entidades);

        assertEquals(entidades.get(0), rankingList.get(0));
        assertEquals(entidades.get(1), rankingList.get(1));
        assertEquals(entidades.get(2), rankingList.get(2));
    }

    private List<Entidad> createEntitiesWithIncidents(int numEntidades) {
        List<Entidad> entities = new ArrayList<>();
        for (int i = 1; i <= numEntidades; i++) {
            Entidad entidad = createEntityWithIncidents("Entidad " + i, i);
            entities.add(entidad);
        }
        return entities;
    }

    private Entidad createEntityWithIncidents(String name, int numEstablecimientos) {
        Entidad entidad = new Entidad(name, new Localizacion());
        for (int i = 0; i < numEstablecimientos; i++) {
            Establecimiento establecimiento = createEstablecimientoWithIncidents("Establecimiento " + (i + 1));
            entidad.agregarEstablecimiento(establecimiento);
        }
        return entidad;
    }

    private Establecimiento createEstablecimientoWithIncidents(String name) {
        Establecimiento establecimiento = new Establecimiento(name, new Localizacion());
        List<PrestacionDeServicio> prestaciones = createPrestaciones(3);
        for (PrestacionDeServicio prestacion : prestaciones) {
            establecimiento.agregarServicioPrestado(prestacion);
            createIncidents(prestacion, 3);
        }
        return establecimiento;
    }

    private List<PrestacionDeServicio> createPrestaciones(int num) {
        List<PrestacionDeServicio> prestaciones = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            prestaciones.add(new PrestacionDeServicio(new Servicio("Servicio " + (i + 1)), "Prestacion " + (i + 1), new UbicacionExacta(i + 1, i + 1)));
        }
        return prestaciones;
    }

    private void createIncidents(PrestacionDeServicio prestacion, int numIncidents) {
        for (int i = 0; i < numIncidents; i++) {
            Usuario usuario = new Usuario("Usuario", "Apellido", "");
            int minutesBeforeNow = (i + 1) * 10; // Incrementing time for each incident
            Date incidentDate = generateIncidentDate(minutesBeforeNow);
            Incidente incidente = new Incidente(usuario, "Observaciones", prestacion, incidentDate);
            incidente.cerrar();
        }
    }

    private Date generateIncidentDate(int minutesBeforeNow) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -minutesBeforeNow);
        return calendar.getTime();
    }
}

