package Modelado;

import models.comunidades.Comunidad;
import models.comunidades.Membresia;
import models.usuario.Rol;
import models.localizacion.UbicacionExacta;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import models.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComunidadesTest {
    private Comunidad comunidad1;
    private Usuario franco;
    private Usuario juan;

    private Servicio servicio;

    private PrestacionDeServicio banioMedrano;

    private PrestacionDeServicio banioCastroBarros;

    private Comunidad comunidad2;

    private Comunidad comunidad3;


    @BeforeEach
    public void setUp() throws Exception {

        // Creamos la prestacion de prestacion de servicio
        servicio = new Servicio("baño hombres");
        banioMedrano = new PrestacionDeServicio(servicio, "baño Medrano", new UbicacionExacta(1, 1));
        banioCastroBarros= new PrestacionDeServicio(servicio, "baño Castro Barros", new UbicacionExacta(2, 2));

        // Creamos las 3 comunidades
        comunidad1 = new Comunidad("comunidad1");
        comunidad1.agregarServicioDeInteres(banioMedrano);
        comunidad2 = new Comunidad("comunidad2");
        comunidad2.agregarServicioDeInteres(banioMedrano);
        comunidad3 = new Comunidad("comunidad3");
        comunidad3.agregarServicioDeInteres(banioMedrano);
        comunidad3.agregarServicioDeInteres(banioCastroBarros);

        this.franco = new Usuario("franco", "pesce", "francopescee@gmail.com");

        this.juan = new Usuario("juan", "perez", "");
    }

    @Test
    public void testAgregarUsuarioAComunidadVacia() {
        Membresia membresiaNueva = new Membresia(comunidad1, franco, new Rol());
        franco.unirseAComunidad(membresiaNueva);
        comunidad1.agregarMembresia(membresiaNueva);

        assertEquals(franco.getMembresias().size(), 1);
        assertEquals(comunidad1.getMembresias().size(), 1);
    }


    @Test
    public void eliminarUsuarioDeComunidad() throws Exception {
        Membresia membresiaNueva = new Membresia(comunidad1, franco, new Rol());
        franco.unirseAComunidad(membresiaNueva);

        franco.abandonarComunidad(comunidad1);
        comunidad1.eliminarMemebresia(membresiaNueva);

        assertEquals(comunidad1.getMembresias().size(), 0);
        assertEquals(franco.getMembresias().size(), 0);
    }

    @Test
    public void testCantidadDeAfectados(){
        Membresia membresiaNueva = new Membresia(comunidad1, franco, new Rol());
        franco.unirseAComunidad(membresiaNueva);
        comunidad1.agregarMembresia(membresiaNueva);
        franco.getMembresia(comunidad1).cambiarAfectacion(banioMedrano, true);
        assertEquals(comunidad1.getCantidadDeAfectados(banioMedrano), 1);

    }
}
