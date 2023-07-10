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
import notificaciones.FactoryConfiguracionDeNotificaciones;
import notificaciones.FactoryNotificacion;
import notificaciones.Notificacion;
import notificaciones.notificador.AperturaDeIncidente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.List;

public class NotificacionesTest {
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

        emailfede.nombreDeUsuario = "tandres";
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
        comunidad1.aceptarUsuario(fede);

        franco.unirseAComunidad(comunidad1, comunidad1.getRoles().get(0));
        franco.unirseAComunidad(comunidad2, comunidad2.getRoles().get(0));
        fede.unirseAComunidad(comunidad2, comunidad2.getRoles().get(0));
        fede.unirseAComunidad(comunidad3, comunidad3.getRoles().get(0));
    }

    // casos a testear:
    // 1. medio = "Mail" y estrategia = "Cuando suceden"
    // 2. medio = "Mail" y estrategia = "Sin apuros"
    // 3. medio = "Whatsapp" y estrategia = "Cuando suceden"
    // 4. medio = "Whatsapp" y estrategia = "Sin apuros"

    // se envia una sola notificacion a franco por mail cuando se crea un incidente en la comunidad1
    @Test
    public void testSeEnvia1NotificacionAFrancoPorMailCuandoSeCreaUnIncidenteEnLaComunidad1() {
        Incidente incidente = new Incidente(franco, "baño sucio", banioMedrano);
        franco.getComunidades().forEach(comunidad -> comunidad.abrirIncidente(incidente));

        // Mockeamos el metodo notificar de la clase AdapterMail
        AdapterMail adapterMail = Mockito.mock(AdapterMail.class);
    }
}

