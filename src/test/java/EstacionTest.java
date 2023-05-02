import org.junit.jupiter.api.Test;
import servicios.*;
import servicios.clasesAuxiliares.Coordenadas;
import servicios.clasesAuxiliares.TipoMedioDeTransporte;
import servicios.serviciosPublicos.Ascensor;
import servicios.serviciosPublicos.Banio;
import servicios.serviciosPublicos.EscaleraMecanica;
import servicios.serviciosPublicos.Servicio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class EstacionTest {

    @Test
    public void testTodosLosServiciosFuncionan() {

        Servicio escaleraMecanica = new EscaleraMecanica();
        Servicio ascensor = new Ascensor();
        Servicio banio = new Banio();

        Set<Servicio> servicios = new HashSet<Servicio>();
        servicios.add(escaleraMecanica);
        servicios.add(ascensor);
        servicios.add(banio);

        Estacion plazaDeMayo = new Estacion("Plaza de Mayo", new Coordenadas(-34.60871, -58.37149), servicios);

        ArrayList<Estacion> estaciones = new ArrayList<Estacion>();
        estaciones.add(plazaDeMayo);

        Linea lineaA = new Linea(estaciones, "A", TipoMedioDeTransporte.SUBTERRANEO);

        assertTrue(lineaA.estacionDeOrigen().todosLosServiciosFuncionan());
    }

    @Test
    public void testNoFuncionaElBanioDeHombres(){
        Servicio escaleraMecanica = new EscaleraMecanica();
        Servicio ascensor = new Ascensor();
        Servicio banioMujeres = new Banio();
        Servicio banioHombres = new Banio();

        Set<Servicio> servicios = new HashSet<Servicio>();
        servicios.add(escaleraMecanica);
        servicios.add(ascensor);
        servicios.add(banioMujeres);
        servicios.add(banioHombres);

        Estacion plazaDeMayo = new Estacion("Plaza de Mayo", new Coordenadas(-34.60871, -58.37149), servicios);

        ArrayList<Estacion> estaciones = new ArrayList<Estacion>();
        estaciones.add(plazaDeMayo);

        Linea lineaA = new Linea(estaciones, "A", TipoMedioDeTransporte.SUBTERRANEO);

        banioHombres.setFunciona(false);

        assertFalse(lineaA.estacionDeOrigen().todosLosServiciosFuncionan());
    }
}

