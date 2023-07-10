import comunidades.Comunidad;
import comunidades.usuario.configuraciones.formas.CuandoSuceden;
import comunidades.usuario.configuraciones.formas.EstrategiaDeNotificacion;
import comunidades.usuario.configuraciones.formas.SinApuros;
import comunidades.usuario.configuraciones.medios.MedioPreferido;
import comunidades.usuario.configuraciones.medios.mail.NotificarPorMail;
import comunidades.usuario.configuraciones.medios.whatsapp.AdapterWhatsapp;
import comunidades.usuario.configuraciones.medios.whatsapp.NotificarPorWhatsApp;
import incidentes.Incidente;
import incidentes.RevisionDeIncidente;
import org.mockito.Mock;
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

    @Test
    public void testEnviaNotificacionAFrancoCuandoCreaUnIncidente() {
        // Seteamos la configuracion de notificaciones de franco y fede como mocks
        franco.setConfiguracionDeNotificaciones(configMockFranco);
        fede.setConfiguracionDeNotificaciones(configMockFede);

        // Creamos el incidente
        Incidente incidente = new Incidente(franco, "baño sucio",banioMedrano);

        Mockito.verify(configMockFranco, Mockito.times(1)).notificar(Mockito.any(Notificacion.class));
    }

    @Test
    public void testEnviaNotificacionAFedeCuandoFrancoCreaUnIncidente(){
        // Seteamos la configuracion de notificaciones de franco y fede como mocks
        franco.setConfiguracionDeNotificaciones(configMockFranco);
        fede.setConfiguracionDeNotificaciones(configMockFede);

        Incidente incidente = new Incidente(franco, "baño sucio",banioMedrano);

        Mockito.verify(configMockFede, Mockito.times(1)).notificar(Mockito.any(Notificacion.class));
    }

    @Test
    public void testNoSeLeEnviaNotificacionAFedeCuandoFrancoCreaUnIncidenteEnOtraComunidad() throws Exception {
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
    public void testSeLeEnviaNotificacionAFedePorEstarInteresado(){
        franco.setConfiguracionDeNotificaciones(configMockFranco);
        fede.setConfiguracionDeNotificaciones(configMockFede);

        // Franco crea el incidente en el baño de castro barros
        Incidente incidente = new Incidente(franco, "baño sucio",banioCastroBarros);

        // Aunque franco no este en la comunidad 3, fede esta interesado en el baño de castro barros y se le deberia notificar
        Mockito.verify(configMockFede, Mockito.times(1)).notificar(Mockito.any(Notificacion.class));
    }

    @Test
    public void testSeEnviaWhatsappAFrancoCuandoCreaIncidente() {
        MedioPreferido medioPreferidoFranco = Mockito.mock(NotificarPorWhatsApp.class);
        franco.getConfiguracionDeNotificaciones().setMedioPreferido(medioPreferidoFranco);
        fede.setConfiguracionDeNotificaciones(configMockFede);

        Incidente incidente = new Incidente(franco, "baño sucio", banioMedrano);

        Mockito.verify(medioPreferidoFranco, Mockito.times(1)).notificar(Mockito.any(Notificacion.class));
    }

    @Test
    public void testSeEnviaMailAFrancoCuandoCreaIncidente() {
        MedioPreferido medioPreferidoFranco = Mockito.mock(NotificarPorMail.class);
        franco.getConfiguracionDeNotificaciones().setMedioPreferido(medioPreferidoFranco);
        fede.setConfiguracionDeNotificaciones(configMockFede);

        Incidente incidente = new Incidente(franco, "baño sucio", banioMedrano);

        Mockito.verify(medioPreferidoFranco, Mockito.times(1)).notificar(Mockito.any(Notificacion.class));
    }

    @Test
    public void testSeEnviaNotificacionCuandoSucedeAFrancoCuandoCreaIncidente() {
        EstrategiaDeNotificacion estrategiaDeNotificacionFranco = Mockito.mock(CuandoSuceden.class);
        franco.getConfiguracionDeNotificaciones().setEstrategiaDeNotificacion(estrategiaDeNotificacionFranco);
        fede.setConfiguracionDeNotificaciones(configMockFede);

        Incidente incidente = new Incidente(franco, "baño sucio", banioMedrano);

        Mockito.verify(estrategiaDeNotificacionFranco, Mockito.times(1)).notificar(Mockito.any(Notificacion.class));
    }

    @Test
    public void testSeEnviaNotificacionSinApurosAFrancoCuandoCreaIncidente() {
        EstrategiaDeNotificacion estrategiaDeNotificacionFranco = Mockito.mock(SinApuros.class);
        franco.getConfiguracionDeNotificaciones().setEstrategiaDeNotificacion(estrategiaDeNotificacionFranco);
        fede.setConfiguracionDeNotificaciones(configMockFede);

        Incidente incidente = new Incidente(franco, "baño sucio", banioMedrano);

        Mockito.verify(estrategiaDeNotificacionFranco, Mockito.times(1)).notificar(Mockito.any(Notificacion.class));
    }

    @Test
    public void testSeEnviaNotificacionDeCierreDeIncidente(){
        franco.setConfiguracionDeNotificaciones(configMockFranco);
        fede.setConfiguracionDeNotificaciones(configMockFede);

        // Franco crea el incidente
        Incidente incidente = new Incidente(franco, "baño sucio", banioMedrano);
        // Se abre el incidente en todas las comunidades de franco
        franco.getComunidades().forEach(comunidad -> comunidad.abrirIncidente(incidente));
        // Se cierra el incidente
        comunidad2.cerrarIncidente(incidente, fede);

        // fede deberia recibir 2 notificaciones
        Mockito.verify(configMockFede, Mockito.times(2)).notificar(Mockito.any(Notificacion.class));
    }

    @Test
    public void testSeEnviaNotificacionDeRevisionDeIncidente(){
        franco.setConfiguracionDeNotificaciones(configMockFranco);
        fede.setConfiguracionDeNotificaciones(configMockFede);

        // Franco crea el incidente
        RevisionDeIncidente revisionDeIncidente = new RevisionDeIncidente();

        // fede deberia recibir 2 notificaciones
        Mockito.verify(configMockFede, Mockito.times(2)).notificar(Mockito.any(Notificacion.class));
    }
}

