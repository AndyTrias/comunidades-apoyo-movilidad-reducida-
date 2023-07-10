import comunidades.Comunidad;
import incidentes.Incidente;
import servicios.PrestacionDeServicio;
import servicios.Servicio;
import comunidades.usuario.Email;
import comunidades.usuario.Usuario;
import comunidades.usuario.configuraciones.ConfiguracionDeNotificaciones;
import notificaciones.FactoryConfiguracionDeNotificaciones;
import notificaciones.Notificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NotificacionesTest {
    private Comunidad comunidad1;
    private Comunidad comunidad2;
    private Comunidad comunidad3;

    private Usuario franco;
    private Usuario fede;
    private ConfiguracionDeNotificaciones configMockFranco;
    private ConfiguracionDeNotificaciones configMockFede;

    private PrestacionDeServicio banioMedrano;
    private PrestacionDeServicio banioCastroBarros;
    private Servicio servicio;

    @BeforeEach
    public void setUp() throws Exception {
        // Creamos la prestacion de prestacion de servicio
        servicio = new Servicio("baño hombres");
        banioMedrano = new PrestacionDeServicio(servicio, "baño Medrano");
        banioCastroBarros = new PrestacionDeServicio(servicio, "baño Castro Barros");

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

        emailfede.nombreDeUsuario = "tsdaandres";
        emailfede.dominio = "frba.utn.edu.ar";

        // Creamos los 2 usuarios
        franco = new Usuario("franco", "pesce", emailfranco);
        fede = new Usuario("fede", "perez", emailfede);

        // Creamos la configuracion de notificaciones
        ConfiguracionDeNotificaciones config = FactoryConfiguracionDeNotificaciones.crearConfiguracionDeNotificaciones("M_C");
        franco.setConfiguracionDeNotificaciones(config);
        fede.setConfiguracionDeNotificaciones(config);

        // Agregamos los usuarios a las comunidades y les asignamos un rol
        comunidad1.aceptarUsuario(franco);
        comunidad2.aceptarUsuario(franco);
        comunidad2.aceptarUsuario(fede);
        comunidad3.aceptarUsuario(fede);

        franco.unirseAComunidad(comunidad1, comunidad1.getRoles().get(0));
        franco.unirseAComunidad(comunidad2, comunidad2.getRoles().get(0));
        fede.unirseAComunidad(comunidad2, comunidad2.getRoles().get(0));
        fede.unirseAComunidad(comunidad3, comunidad3.getRoles().get(0));

        this.configMockFranco = Mockito.mock(ConfiguracionDeNotificaciones.class);
        this.configMockFede = Mockito.mock(ConfiguracionDeNotificaciones.class);
    }

    // casos a testear:
    // 1. medio = "Mail" y estrategia = "Cuando suceden"
    // 2. medio = "Mail" y estrategia = "Sin apuros"
    // 3. medio = "Whatsapp" y estrategia = "Cuando suceden"
    // 4. medio = "Whatsapp" y estrategia = "Sin apuros"

    // se envia una sola notificacion a franco por mail cuando se crea un incidente en la comunidad1
    @Test
    public void testEnviaMailAFrancoCuandoCreaUnIncidente() {
        // Seteamos la configuracion de notificaciones de franco y fede como mocks
        franco.setConfiguracionDeNotificaciones(configMockFranco);
        fede.setConfiguracionDeNotificaciones(configMockFede);

        // Creamos el incidente
        Incidente incidente = new Incidente(franco, "baño sucio",banioMedrano);

        Mockito.verify(configMockFranco, Mockito.times(1)).notificar(Mockito.any(Notificacion.class));
    }

    @Test
    public void testEnviaMailAFedeCuandoFrancoCreaUnIncidente(){
        // Seteamos la configuracion de notificaciones de franco y fede como mocks
        franco.setConfiguracionDeNotificaciones(configMockFranco);
        fede.setConfiguracionDeNotificaciones(configMockFede);

        Incidente incidente = new Incidente(franco, "baño sucio",banioMedrano);

        Mockito.verify(configMockFede, Mockito.times(1)).notificar(Mockito.any(Notificacion.class));
    }

    @Test
    public void testNoSeLeEnviaMailAFedeCuandoFrancoCreaUnIncidenteEnOtraComunidad() throws Exception {
        // Franco se va de la comunidad 2 entonces no comparten comunidad
        comunidad2.eliminarUsuario(franco);
        franco.abandonarComunidad(comunidad2);

        // Seteamos la configuracion de notificaciones de franco y fede como mocks
        franco.setConfiguracionDeNotificaciones(configMockFranco);
        fede.setConfiguracionDeNotificaciones(configMockFede);

        // Franco crea el incidente
        Incidente incidente = new Incidente(franco, "baño sucio",banioMedrano);

        Mockito.verify(configMockFede, Mockito.times(0)).notificar(Mockito.any(Notificacion.class));
    }

    @Test
    public void testSeLeEnviaMailAFedePorEstarInteresado(){
        franco.setConfiguracionDeNotificaciones(configMockFranco);
        fede.setConfiguracionDeNotificaciones(configMockFede);

        // Franco crea el incidente en el baño de castro barros
        Incidente incidente = new Incidente(franco, "baño sucio",banioCastroBarros);

        // Aunque franco no este en la comunidad 3, fede esta interesado en el baño de castro barros y se le deberia notificar
        Mockito.verify(configMockFede, Mockito.times(1)).notificar(Mockito.any(Notificacion.class));
    }
}

