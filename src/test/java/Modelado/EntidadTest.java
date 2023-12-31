package Modelado;

import models.entidades.Entidad;
import models.entidades.Establecimiento;
import models.localizacion.Localizacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EntidadTest {
  private Entidad entidad;
  private Establecimiento establecimiento1;
  private Establecimiento establecimiento2;

  @BeforeEach
  public void setUp() {
    entidad = new Entidad("Santander Rio Argentina", new Localizacion());
    establecimiento1 = new Establecimiento("Sucursal Almagro", new Localizacion());
    establecimiento2 = new Establecimiento("Sucursal Buenos Aires", new Localizacion());
  }

  @Test
  public void testAgregarEstablecimiento() {
    entidad.agregarEstablecimiento(establecimiento1);
    Set<Establecimiento> establecimientos = entidad.getEstablecimientos();
    assertEquals(1, establecimientos.size());
    assertTrue(establecimientos.contains(establecimiento1));
  }

  @Test
  public void testAgregarEstablecimientoDuplicado() {
    entidad.agregarEstablecimiento(establecimiento1);
    entidad.agregarEstablecimiento(establecimiento1);
    Set<Establecimiento> establecimientos = entidad.getEstablecimientos();
    assertEquals(1, establecimientos.size());
  }

  @Test
  public void testAgregarMultiplesEstablecimientos() {
    entidad.agregarEstablecimiento(establecimiento1);
    entidad.agregarEstablecimiento(establecimiento2);
    Set<Establecimiento> establecimientos = entidad.getEstablecimientos();
    assertEquals(2, establecimientos.size());
    assertTrue(establecimientos.contains(establecimiento1));
    assertTrue(establecimientos.contains(establecimiento2));
  }
}
