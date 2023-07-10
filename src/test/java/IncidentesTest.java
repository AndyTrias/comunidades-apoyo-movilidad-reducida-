import comunidades.Comunidad;
import comunidades.incidentes.Incidente;
import comunidades.servicios.PrestacionDeServicio;
import comunidades.servicios.Servicio;
import comunidades.usuario.Email;
import comunidades.usuario.Usuario;
import comunidades.usuario.configuraciones.ConfiguracionDeNotificaciones;
import comunidades.usuario.configuraciones.formas.CuandoSuceden;
import comunidades.usuario.configuraciones.formas.EstrategiaDeNotificacion;
import comunidades.usuario.configuraciones.medios.mail.AdapterMail;
import comunidades.usuario.configuraciones.medios.mail.NotificarPorMail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IncidentesTest {
  private Comunidad comunidad1;
  private Comunidad comunidad2;
  private Comunidad comunidad3;

  private Usuario franco;
  private Usuario fede;

  private PrestacionDeServicio banioMedrano;
  private PrestacionDeServicio banioCastroBarros;
  private Servicio servicio;

  @BeforeEach
  public void setUp() throws Exception {
    // Creamos la prestacion de prestacion de servicio
    servicio = new Servicio("baño hombres");
    banioMedrano = new PrestacionDeServicio(servicio, "baño Medrano");
    banioCastroBarros= new PrestacionDeServicio(servicio, "baño Castro Barros");

    // Creamos las 3 comunidades
    comunidad1 = new Comunidad("comunidad1");
    comunidad1.agregarServicioDeInteres(banioMedrano);
    comunidad2 = new Comunidad("comunidad2");
    comunidad2.agregarServicioDeInteres(banioMedrano);
    comunidad3 = new Comunidad("comunidad3");
    comunidad3.agregarServicioDeInteres(banioMedrano);
    comunidad3.agregarServicioDeInteres(banioCastroBarros);
    
    Email emailfranco = new Email();
    Email emailfede = new Email();

    emailfranco.nombreDeUsuario = "francopescee";
    emailfranco.dominio = "gmail.com";

    emailfede.nombreDeUsuario = "tandres";
    emailfede.dominio = "frba.utn.edu.ar";

    // Creamos los 2 usuarios
    franco = new Usuario("franco", "pesce", emailfranco);
    fede = new Usuario("fede", "perez", emailfede);

    // agregamos los usuarios a las comunidades
    franco.unirseAComunidad(comunidad1, comunidad1.getRoles().get(0));
    franco.unirseAComunidad(comunidad2, comunidad2.getRoles().get(0));
    fede.unirseAComunidad(comunidad2, comunidad2.getRoles().get(0));
    fede.unirseAComunidad(comunidad3, comunidad3.getRoles().get(0));
  }

  @Test
  public void testAperturaDeIncidente() {
    // Franco crea el incidente en Medrano
    Incidente incidente = new Incidente(franco, "observaciones", banioMedrano);
    franco.getComunidades().forEach(c -> c.abrirIncidente(incidente));

    // Verificamos que c1 y c2 tenga ese indcidente abierto
    assertEquals(comunidad1.getIncidentesAbiertos().size(), 1);
    assertEquals(comunidad2.getIncidentesAbiertos().size(), 1);

    // Verificamos que el incidente este en el baño
    assertEquals(banioMedrano.getIncidentes().size(), 1);
  }

  @Test
  public void testDeAperturadeIndicentesRepetidos() {

    // Franco crea el incidente en c1 y c2
    Incidente incidente = new Incidente(franco, "observaciones", banioMedrano);
    franco.getComunidades().forEach(c -> c.abrirIncidente(incidente));

    // Fede crea el incidente en c3.
    Incidente incidente2 = new Incidente(fede, "observaciones", banioMedrano);
    franco.getComunidades().forEach(c -> c.abrirIncidente(incidente2));

    // Verificamos que el incidente no se creo dos veces en c2
    // Vericamos que el banio medrano si tiene dos incidentes
    assertEquals(comunidad2.getIncidentesAbiertos().size(), 1);
    assertEquals(banioMedrano.getIncidentes().size(), 2);
  }

  public void testSeAbreElIncidenteEnComunidadesPertinente(){
    // Fede abre el incidente
    Incidente incidenteCastroBarros = new Incidente(fede, "observaciones", banioCastroBarros);
    fede.getComunidades().forEach(c -> c.abrirIncidente(incidenteCastroBarros));

    // Debemos validar que no se abrio en C1 porque fede no participa
    // Debemos validar que no se abrio en C2 porque no es de su interes castro barros
    assertEquals(comunidad1.getIncidentesAbiertos().size(), 0);
    assertEquals(comunidad2.getIncidentesAbiertos().size(), 0);
    assertEquals(comunidad3.getIncidentesAbiertos().size(), 1);
    assertEquals(banioCastroBarros.getIncidentes().size(), 1);


  }


  @Test
  public void testCerrarIncidente() {

    // Franco crea un incidente
    Incidente incidente = new Incidente(franco, "observaciones", banioMedrano);
    franco.getComunidades().forEach(c -> c.abrirIncidente(incidente));

    // Fede cierra el incidente
    fede.getComunidades().get(0).cerrarIncidente(incidente, fede);

    assertEquals(comunidad2.getIncidentesCerrados().size(), 1);

  }

  @Test
  public void testDeCierreDeIncidentesRepetidos() {

    // Franco crea un incidente en Medrano
    Incidente incidenteMedrano = new Incidente(franco, "observaciones", banioMedrano);
    franco.getComunidades().forEach(c -> c.abrirIncidente(incidenteMedrano));


    // Fede cierra el incidente de Medrano en sus comunidades
    // Necesitamos filtrar las comunidades que tienen ese incidente
    // y cerrarlo en cada una de ellas
    fede.getComunidades().stream().filter(c -> c.getIncidentesAbiertos().contains(incidenteMedrano)).forEach(c -> c.cerrarIncidente(incidenteMedrano, fede));


    assertEquals(comunidad1.getIncidentesCerrados().size(), 0);
    assertEquals(comunidad2.getIncidentesCerrados().size(), 1);

    // En la comunidad 3 no se abrio el incidente
    assertEquals(comunidad3.getIncidentesCerrados().size(), 0);
    assertEquals(incidenteMedrano.getFechasDeCierre().size(), 1);
  }


  @Test
  public void testdeCierreIncidentesAdecuados() {

    // Franco crea un incidente en Medrano
    Incidente incidenteMedrano = new Incidente(franco, "observaciones", banioMedrano);
    franco.getComunidades().forEach(c -> c.abrirIncidente(incidenteMedrano));

    // Fede crea un incidente en Castro Barros
    Incidente incidenteCastroBarros = new Incidente(fede, "observaciones", banioCastroBarros);
    fede.getComunidades().forEach(c -> c.abrirIncidente(incidenteCastroBarros));


    // Fede cierra el incidente de Medrano en sus comunidades
    fede.getComunidades().stream().filter(c -> c.getIncidentesAbiertos().contains(incidenteMedrano)).forEach(c -> c.cerrarIncidente(incidenteMedrano, fede));

    // Debemos validar que el incidente en Castro Barros no se haya cerrado
    assertEquals(comunidad3.getIncidentesCerrados().size(), 0);
    assertEquals(incidenteCastroBarros.getFechasDeCierre().size(), 0);
  }



  @Test
  public void fechasDeCierre() {
    Incidente incidente = new Incidente(franco, "observaciones", banioMedrano);

    for (int i = 0; i < 3; i++) {
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DAY_OF_YEAR, +i); // Restar 1 día

      // Obtener una instancia de Date con la fecha de ayer
      Date fecha = calendar.getTime();
      incidente.getFechasDeCierre().add(fecha);
    }

    assertEquals(incidente.tiempoActivo(), 1440);
  }

}