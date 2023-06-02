/*
import comunidades.servicios.Servicio;
import org.junit.jupiter.api.Test;
import servicios.*;
import servicios.clasesAuxiliares.Coordenadas;
import servicios.clasesAuxiliares.TipoMedioDeTransporte;
import servicios.serviciosPublicos.Ascensor;
import servicios.serviciosPublicos.Banio;
import servicios.serviciosPublicos.EscaleraMecanica;
import servicios.serviciosPublicos.Servicio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ServiciosDeEstacionTest {
    // Creamos los servicios
    Servicio escaleraMecanica = new EscaleraMecanica();
    Servicio ascensor = new Ascensor();
    Servicio banioHombres = new Banio();
    Servicio banioMujeres = new Banio();

    // Creamos el set de servicios
    Set<Servicio> servicios = new HashSet<Servicio>();

    // Creamos la estacion
    Estacion plazaDeMayo = new Estacion("Plaza de Mayo", new Coordenadas(-34.60871, -58.37149), servicios);

    // Creamos el array de estaciones
    ArrayList<Estacion> estaciones = new ArrayList<Estacion>();

    // Creamos la linea
    Linea lineaA = new Linea(estaciones, "A", TipoMedioDeTransporte.SUBTERRANEO);
    @Test
    public void testTodosLosServiciosFuncionan_TodoFunciona() {
        servicios.add(escaleraMecanica);
        servicios.add(ascensor);
        servicios.add(banioHombres);
        servicios.add(banioMujeres);

        estaciones.add(plazaDeMayo);

        assertTrue(lineaA.estacionDeOrigen().todosLosServiciosFuncionan());
    }

    @Test
    public void testTodosLosServiciosFuncionan_NoFuncionaElBanioDeHombres(){

        servicios.add(escaleraMecanica);
        servicios.add(ascensor);
        servicios.add(banioMujeres);
        servicios.add(banioHombres);

        estaciones.add(plazaDeMayo);

        banioHombres.setFunciona(false);

        assertFalse(lineaA.estacionDeOrigen().todosLosServiciosFuncionan());
    }

    @Test
    public void testServiciosQueNoFuncionan_FuncionaTodo(){
        servicios.add(escaleraMecanica);
        servicios.add(ascensor);
        servicios.add(banioMujeres);
        servicios.add(banioHombres);

        estaciones.add(plazaDeMayo);

        assertTrue(lineaA.estacionDeOrigen().serviciosQueNoFuncionan().isEmpty());
    }

    @Test
    public void testServiciosQueNoFuncionan_BanioYEscalera(){
        servicios.add(escaleraMecanica);
        servicios.add(ascensor);
        servicios.add(banioMujeres);
        servicios.add(banioHombres);

        estaciones.add(plazaDeMayo);

        banioHombres.setFunciona(false);
        escaleraMecanica.setFunciona(false);

        assertEquals(2, lineaA.estacionDeOrigen().serviciosQueNoFuncionan().size());
    }
}
*/
