package Modelado;

import comunidades.Comunidad;
import incidentes.Incidente;
import localizacion.UbicacionExacta;
import servicios.PrestacionDeServicio;
import servicios.Servicio;
import usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class IncidentesTest {
  private Comunidad comunidad1;
  private Comunidad comunidad2;
  private Comunidad comunidad3;

  private Usuario andy;
  private Usuario fede;

  private PrestacionDeServicio banioMedrano;
  private PrestacionDeServicio banioCastroBarros;
  private Servicio servicio;

  @BeforeEach
  public void setUp() throws Exception {
    // Creamos la prestacion de servicio -> Baños Medrano y Castro Barros
    servicio = new Servicio("baño hombres");
    banioMedrano = new PrestacionDeServicio(servicio, "baño Medrano", new UbicacionExacta(1, 1));
    banioCastroBarros = new PrestacionDeServicio(servicio, "baño Castro Barros", new UbicacionExacta(2, 2));

    // Creamos las 3 comunidades
    comunidad1 = new Comunidad("comunidad1");
    comunidad1.agregarServicioDeInteres(banioMedrano);
    comunidad2 = new Comunidad("comunidad2");
    comunidad2.agregarServicioDeInteres(banioMedrano);
    comunidad3 = new Comunidad("comunidad3");
    comunidad3.agregarServicioDeInteres(banioMedrano);
    comunidad3.agregarServicioDeInteres(banioCastroBarros);

    // Creamos los 2 usuarios
    andy = new Usuario("andy", "trias", "francopescee@gmail.com");
    fede = new Usuario("fede", "perez", "tandres@frba.utn.edu.ar");

    // agregamos los usuarios a las comunidades
    andy.unirseAComunidad(comunidad1, comunidad1.getRoles().get(0));
    andy.unirseAComunidad(comunidad2, comunidad2.getRoles().get(0));
    fede.unirseAComunidad(comunidad2, comunidad2.getRoles().get(0));
    fede.unirseAComunidad(comunidad3, comunidad3.getRoles().get(0));
  }

  @Test
  public void testAperturaDeIncidente() {
    // Andy crea el incidente en Medrano
    Incidente incidente = new Incidente(andy, "observaciones", banioMedrano);
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidente));

    // Verificamos que c1 y c2 tenga ese indcidente abierto
    assertEquals(comunidad1.getIncidentesAbiertos().size(), 1);
    assertEquals(comunidad2.getIncidentesAbiertos().size(), 1);

    // Verificamos que el incidente este en el baño
    assertEquals(banioMedrano.getIncidentes().size(), 1);
  }

  @Test
  public void testDeAperturadeIndicentesRepetidos() {

    // Andy crea el incidente en c1 y c2
    Incidente incidente = new Incidente(andy, "observaciones", banioMedrano);
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidente));

    // Fede crea el incidente en c3.
    Incidente incidente2 = new Incidente(fede, "observaciones", banioMedrano);
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidente));

    // Verificamos que el incidente no se creo dos veces en c2
    // Vericamos que el banio medrano si tiene dos incidentes
    assertEquals(comunidad2.getIncidentesAbiertos().size(), 1);
    assertEquals(banioMedrano.getIncidentes().size(), 2);
  }

  @Test
  public void testSeAbreElIncidenteEnComunidadesPertinente() {
    // Fede abre el incidente
    Incidente incidenteCastroBarros = new Incidente(fede, "observaciones", banioCastroBarros);
    fede.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioCastroBarros)).forEach(c -> c.abrirIncidente(incidenteCastroBarros));

    // Debemos validar que no se abrio en C1 porque fede no participa
    // Debemos validar que no se abrio en C2 porque no es de su interes castro barros
    assertEquals(comunidad1.getIncidentesAbiertos().size(), 0);
    assertEquals(comunidad2.getIncidentesAbiertos().size(), 0);
    assertEquals(comunidad3.getIncidentesAbiertos().size(), 1);
    assertEquals(banioCastroBarros.getIncidentes().size(), 1);
  }


  @Test
  public void testCerrarIncidente() {

    // Andy crea un incidente
    Incidente incidente = new Incidente(andy, "observaciones", banioMedrano);
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidente));

    // Fede cierra el incidente
    fede.getComunidades().stream().filter(c -> c.getIncidentesAbiertos().contains(incidente)).forEach(c -> c.cerrarIncidente(incidente, fede));

    assertEquals(comunidad2.getIncidentesCerrados().size(), 1);
    assertEquals(incidente.getFechasDeCierre().size(), 1);

  }

  @Test
  public void testNoSePuedeCerrarElMismoIncidenteDosVecesEnUnaComunidad() {

    // Andy crea un incidente
    Incidente incidente = new Incidente(andy, "observaciones", banioMedrano);
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidente));

    // Fede cierra el incidente
    fede.getComunidades().stream().filter(c -> c.getIncidentesAbiertos().contains(incidente)).forEach(c -> c.cerrarIncidente(incidente, fede));

    // Fede intenta cerrar el incidente de nuevo
    assertThrows(RuntimeException.class, () -> {
      fede.getComunidades().get(0).cerrarIncidente(incidente, fede);
    });

  }

  @Test
  public void testDeCierreDeIncidentesRepetidos() {

    // Andy crea un incidente en Medrano
    Incidente incidenteMedrano = new Incidente(andy, "observaciones", banioMedrano);
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidenteMedrano));


    // Fede cierra el incidente de Medrano en sus comunidades
    fede.getComunidades().stream().filter(c -> c.getIncidentesAbiertos().contains(incidenteMedrano)).forEach(c -> c.cerrarIncidente(incidenteMedrano, fede));


    assertEquals(comunidad1.getIncidentesCerrados().size(), 0);
    assertEquals(comunidad2.getIncidentesCerrados().size(), 1);

    // En la comunidad 3 no se abrio el incidente
    assertEquals(comunidad3.getIncidentesCerrados().size(), 0);
    assertEquals(incidenteMedrano.getFechasDeCierre().size(), 1);
  }


  @Test
  public void testdeCierreIncidentesAdecuados() {

    // Andy crea un incidente en Medrano
    Incidente incidenteMedrano = new Incidente(andy, "observaciones", banioMedrano);
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidenteMedrano));

    // Fede crea un incidente en Castro Barros
    Incidente incidenteCastroBarros = new Incidente(fede, "observaciones", banioCastroBarros);
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidenteCastroBarros));

    // Fede cierra el incidente de Medrano en sus comunidades
    fede.getComunidades().stream().filter(c -> c.getIncidentesAbiertos().contains(incidenteMedrano)).forEach(c -> c.cerrarIncidente(incidenteMedrano, fede));

    // Debemos validar que el incidente en Castro Barros no se haya cerrado
    // Validamos la logica del controller
    assertEquals(comunidad3.getIncidentesCerrados().size(), 0);
    assertEquals(incidenteCastroBarros.getFechasDeCierre().size(), 0);
  }

  @Test
  public void ConsultaDeIncidentePorEstado() {

    // Andy crea un incidente en Medrano
    Incidente incidenteMedrano = new Incidente(andy, "observaciones", banioMedrano);
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidenteMedrano));

    // Fede cierra el incidente de Medrano en sus comunidades
    fede.getComunidades().stream().filter(c -> c.getIncidentesAbiertos().contains(incidenteMedrano)).forEach(c -> c.cerrarIncidente(incidenteMedrano, fede));

    // Verifcamos que el incidente tiene una fecha de cierre (para la comunidad 2)
    // En la comunidad 1 sigue abierto
    assertEquals(incidenteMedrano.getFechasDeCierre().size(), 1);
    assertFalse(incidenteMedrano.estaAbierto());
    assertFalse(comunidad1.estaCerradoElIncidente(incidenteMedrano));
    assertTrue(comunidad2.estaCerradoElIncidente(incidenteMedrano));

  }


  @Test
  public void fechasDeCierre() {
    Incidente incidente = new Incidente(andy, "observaciones", banioMedrano);

    for (int i = 0; i < 3; i++) {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DAY_OF_YEAR, +i); // Restar 1 día

      // Obtener una instancia de Date con la fecha de ayer
      Date fecha = calendar.getTime();
      incidente.getFechasDeCierre().add(fecha);
    }

    // Validamos que el incidente estuvo abierto en promedio 1440 minutos
    assertEquals(incidente.getFechasDeCierre().size(), 3);
    assertEquals(incidente.tiempoActivo(), 1440);
  }

}