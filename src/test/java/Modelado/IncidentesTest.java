package Modelado;

import models.comunidades.Comunidad;
import models.comunidades.Membresia;
import models.comunidades.Rol;
import models.incidentes.Incidente;
import models.localizacion.UbicacionExacta;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import models.usuario.Usuario;
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
    Membresia andyCom1 = new Membresia(comunidad1, andy, new Rol());
    andy.unirseAComunidad(andyCom1);
    comunidad1.agregarMembresia(andyCom1);

    Membresia andyCom2 = new Membresia(comunidad2, andy, new Rol());
    andy.unirseAComunidad(andyCom2);
    comunidad2.agregarMembresia(andyCom2);

    Membresia fedeCom2 = new Membresia(comunidad2, fede, new Rol());
    fede.unirseAComunidad(fedeCom2);
    comunidad2.agregarMembresia(fedeCom2);

    Membresia fedeCom3 = new Membresia(comunidad3, fede, new Rol());
    fede.unirseAComunidad(fedeCom3);
    comunidad3.agregarMembresia(fedeCom3);

  }

  @Test
  public void testAperturaDeIncidente() {
    // Andy crea el incidente en Medrano
    Incidente incidente = new Incidente(andy, "observaciones", banioMedrano, new Date());
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidente));

    // Verificamos que c1 y c2 tenga ese indcidente
    assertEquals(comunidad1.getIncidentes().size(), 1);
    assertEquals(comunidad2.getIncidentes().size(), 1);

    // Verificamos que el incidente este en el baño
    assertEquals(banioMedrano.getIncidentes().size(), 1);
  }

  @Test
  public void testDeAperturadeIndicentesRepetidos() {

    // Andy crea el incidente en c1 y c2
    Incidente incidente = new Incidente(andy, "observaciones", banioMedrano, new Date());
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidente));

    // Fede crea el incidente en c3.
    Incidente incidente2 = new Incidente(fede, "observaciones", banioMedrano, new Date());
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidente));

    // Verificamos que el incidente no se creo dos veces en c2
    // Vericamos que el banio medrano si tiene dos incidentes
    assertEquals(comunidad2.getIncidentes().size(), 1);
    assertEquals(banioMedrano.getIncidentes().size(), 2);
  }

  @Test
  public void testSeAbreElIncidenteEnComunidadesPertinente() {
    // Fede abre el incidente
    Incidente incidenteCastroBarros = new Incidente(fede, "observaciones", banioCastroBarros, new Date());
    fede.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioCastroBarros)).forEach(c -> c.abrirIncidente(incidenteCastroBarros));

    // Debemos validar que no se abrio en C1 porque fede no participa
    // Debemos validar que no se abrio en C2 porque no es de su interes castro barros
    assertEquals(comunidad1.getIncidentes().size(), 0);
    assertEquals(comunidad2.getIncidentes().size(), 0);
    assertEquals(comunidad3.getIncidentes().size(), 1);
    assertEquals(banioCastroBarros.getIncidentes().size(), 1);
  }


  @Test
  public void testCerrarIncidente() {

    // Andy crea un incidente
    Incidente incidente = new Incidente(andy, "observaciones", banioMedrano, new Date());
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidente));

    // Fede cierra el incidente
    fede.getComunidades().stream().filter(c -> c.estaAbiertoElIncidente(incidente)).forEach(c -> c.cerrarIncidente(incidente, fede));

    assertFalse(comunidad2.estaAbiertoElIncidente(incidente));
    assertEquals(incidente.getFechasDeCierre().size(), 1);

  }

  @Test
  public void testDeCierreDeIncidentesRepetidos() {

    // Andy crea un incidente en Medrano
    Incidente incidenteMedrano = new Incidente(andy, "observaciones", banioMedrano, new Date());
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidenteMedrano));


    // Fede cierra el incidente de Medrano en sus comunidades
    fede.getComunidades().stream().filter(c -> c.estaAbiertoElIncidente(incidenteMedrano)).forEach(c -> c.cerrarIncidente(incidenteMedrano, fede));


    assertTrue(comunidad1.estaAbiertoElIncidente(incidenteMedrano));
    assertFalse(comunidad2.estaAbiertoElIncidente(incidenteMedrano));

    // En la comunidad 3 no se abrio el incidente
    assertFalse(comunidad3.estaAbiertoElIncidente(incidenteMedrano));

    assertEquals(incidenteMedrano.getFechasDeCierre().size(), 1);
  }


  @Test
  public void testdeCierreIncidentesAdecuados() {

    // Andy crea un incidente en Medrano
    Incidente incidenteMedrano = new Incidente(andy, "observaciones", banioMedrano, new Date());
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidenteMedrano));

    // Fede crea un incidente en Castro Barros
    Incidente incidenteCastroBarros = new Incidente(fede, "observaciones", banioCastroBarros, new Date());
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidenteCastroBarros));

    // Fede cierra el incidente de Medrano en sus comunidades
    fede.getComunidades().stream().filter(c -> c.estaAbiertoElIncidente(incidenteMedrano)).forEach(c -> c.cerrarIncidente(incidenteMedrano, fede));

    // Debemos validar que el incidente en Castro Barros no se haya cerrado
    // Validamos la logica del controller
    assertFalse(comunidad3.estaAbiertoElIncidente(incidenteCastroBarros));
    assertEquals(incidenteCastroBarros.getFechasDeCierre().size(), 0);
  }

  @Test
  public void ConsultaDeIncidentePorEstado() {

    // Andy crea un incidente en Medrano
    Incidente incidenteMedrano = new Incidente(andy, "observaciones", banioMedrano, new Date());
    andy.getComunidades().stream().filter(c -> c.getServiciosDeInteres().contains(banioMedrano)).forEach(c -> c.abrirIncidente(incidenteMedrano));

    // Fede cierra el incidente de Medrano en sus comunidades
    fede.getComunidades().stream().filter(c -> c.estaAbiertoElIncidente(incidenteMedrano)).forEach(c -> c.cerrarIncidente(incidenteMedrano, fede));

    // Verifcamos que el incidente tiene una fecha de cierre (para la comunidad 2)
    // En la comunidad 1 sigue abierto
    assertEquals(incidenteMedrano.getFechasDeCierre().size(), 1);
    assertFalse(incidenteMedrano.estaAbierto());
    assertTrue(comunidad1.estaAbiertoElIncidente(incidenteMedrano));
    assertFalse(comunidad2.estaAbiertoElIncidente(incidenteMedrano));

  }


  @Test
  public void fechasDeCierre() {
    Incidente incidente = new Incidente(andy, "observaciones", banioMedrano, new Date());

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