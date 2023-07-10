import servicios.PrestacionDeServicio;
import servicios.Servicio;
import entidades.Establecimiento;
import localizacion.Localizacion;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import static org.junit.Assert.*;

public class EstablecimientoTest {
  private Establecimiento establecimiento;
  private PrestacionDeServicio banioPlantaBaja;
  private PrestacionDeServicio banioGerentes;
  private PrestacionDeServicio escaleraSuperior;
  private Localizacion localizacion;
  

  @Before
  public void setUp() {
    Servicio banio = new Servicio("Banios");
    Servicio escalera = new Servicio("Escaleras");
    localizacion = new Localizacion();

    establecimiento = new Establecimiento("Sucursal Olivos", localizacion);
    banioPlantaBaja = new PrestacionDeServicio(banio, "Banio Planta Baja");
    escaleraSuperior = new PrestacionDeServicio(escalera, "Escalera Superior");
    banioGerentes =  new PrestacionDeServicio(banio, "Banio Gerentes");
  }

  @Test
  public void testagregarServicioPrestado() {
    establecimiento.agregarServicioPrestado(banioPlantaBaja);
    Set<PrestacionDeServicio> servicios = establecimiento.getServicios();
    assertEquals(1, servicios.size());
    assertTrue(servicios.contains(banioPlantaBaja));
  }

  @Test
  public void testagregarServicioPrestado_Duplicado() {
    establecimiento.agregarServicioPrestado(banioPlantaBaja);
    establecimiento.agregarServicioPrestado(banioPlantaBaja);
    Set<PrestacionDeServicio> servicios = establecimiento.getServicios();
    assertEquals(1, servicios.size());
  }

  @Test
  public void testagregarServicioPrestado_MultiplesServicios() {
    establecimiento.agregarServicioPrestado(banioGerentes);
    establecimiento.agregarServicioPrestado(escaleraSuperior);
    Set<PrestacionDeServicio> servicios = establecimiento.getServicios();
    assertEquals(2, servicios.size());
    assertTrue(servicios.contains(banioGerentes));
    assertTrue(servicios.contains(escaleraSuperior));
  }
}
