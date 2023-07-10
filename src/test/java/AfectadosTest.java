import comunidades.Comunidad;
import servicios.PrestacionDeServicio;
import servicios.Servicio;
import comunidades.usuario.Email;
import comunidades.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class AfectadosTest {

  private Usuario andy;
  private Comunidad comunidad1;
  private Servicio banio;
  private Servicio escalera;
  private PrestacionDeServicio banioMedrano;
  private PrestacionDeServicio escaleraMedrano;

  @BeforeEach
  public void setUp()  {
    this.banio = new Servicio("banio");
    this.escalera = new Servicio("escalera");
    this.banioMedrano = new PrestacionDeServicio(banio, "medrano");
    this.escaleraMedrano = new PrestacionDeServicio(escalera, "medrano");

    this.andy = new Usuario("andy", "perez", new Email());

    this.comunidad1 = new Comunidad("comunidad1");
    this.comunidad1.agregarServicioDeInteres(banioMedrano);
    this.comunidad1.agregarServicioDeInteres(escaleraMedrano);
  }

  @Test
  public void TestMesientoAfectado()
  {
    andy.unirseAComunidad(comunidad1, comunidad1.getRoles().get(0));
    andy.getMembresia(comunidad1).cambiarAfectacion(banioMedrano, true);

    Assertions.assertTrue(andy.getMembresia(comunidad1).getAfectacion(banioMedrano).isAfectado());
  }

}
