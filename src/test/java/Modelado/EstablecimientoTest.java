package Modelado;

import models.localizacion.UbicacionExacta;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import models.entidades.Establecimiento;
import models.localizacion.Localizacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.Set;

public class EstablecimientoTest {
  private Establecimiento establecimiento;
  private PrestacionDeServicio banioPlantaBaja;
  private PrestacionDeServicio banioGerentes;
  private PrestacionDeServicio escaleraSuperior;
  private Localizacion localizacion;
  

  @BeforeEach
  public void setUp() {
    Servicio banio = new Servicio("Banios");
    Servicio escalera = new Servicio("Escaleras");
    localizacion = new Localizacion();

    establecimiento = new Establecimiento("Sucursal Olivos", localizacion);
    banioPlantaBaja = new PrestacionDeServicio(banio, "Banio Planta Baja", new UbicacionExacta(1, 1));
    escaleraSuperior = new PrestacionDeServicio(escalera, "Escalera Superior", new UbicacionExacta(1, 1));
    banioGerentes =  new PrestacionDeServicio(banio, "Banio Gerentes", new UbicacionExacta(1, 1));
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
