package Persistencia;

import models.comunidades.Comunidad;
import models.comunidades.Membresia;
import models.comunidades.Rol;
import models.entidades.Establecimiento;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoEstablecimiento;
import models.servicios.PrestacionDeServicio;
import models.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioApiTest {
    private RepoComunidad repoComunidad;
    private RepoEstablecimiento repoEstablecimiento;

    @BeforeEach
    public void init() {
        repoComunidad = new RepoComunidad();
        repoEstablecimiento = new RepoEstablecimiento();
    }

    @Test
    public void datosParaProbarApi() {
        Comunidad comunidad1 = new Comunidad("falopa1");
        Comunidad comunidad2 = new Comunidad("falopa2");

        Establecimiento establecimiento1 = new Establecimiento();
        Establecimiento establecimiento2 = new Establecimiento();
        Establecimiento establecimiento3 = new Establecimiento();
        Establecimiento establecimiento4 = new Establecimiento();
        Establecimiento establecimiento5 = new Establecimiento();

        PrestacionDeServicio prestacion1 = new PrestacionDeServicio();
        PrestacionDeServicio prestacion2 = new PrestacionDeServicio();
        PrestacionDeServicio prestacion3 = new PrestacionDeServicio();
        PrestacionDeServicio prestacion4 = new PrestacionDeServicio();
        PrestacionDeServicio prestacion5 = new PrestacionDeServicio();

        establecimiento1.agregarServicioPrestado(prestacion1);
        establecimiento2.agregarServicioPrestado(prestacion2);
        establecimiento3.agregarServicioPrestado(prestacion3);
        establecimiento4.agregarServicioPrestado(prestacion4);
        establecimiento5.agregarServicioPrestado(prestacion5);

        comunidad1.agregarServicioDeInteres(prestacion1);
        comunidad1.agregarServicioDeInteres(prestacion2);
        comunidad1.agregarServicioDeInteres(prestacion3);
        comunidad1.agregarServicioDeInteres(prestacion4);

        comunidad2.agregarServicioDeInteres(prestacion1);
        comunidad2.agregarServicioDeInteres(prestacion2);
        comunidad2.agregarServicioDeInteres(prestacion3);
        comunidad2.agregarServicioDeInteres(prestacion5);

        Usuario usuario1 = new Usuario("franco", "pesce", "f@gmail.com");
        Usuario usuario2 = new Usuario("franco", "pesce", "f@gmail.com");
        Usuario usuario3 = new Usuario("franco", "pesce", "f@gmail.com");
        Usuario usuario4 = new Usuario("franco", "pesce", "f@gmail.com");
        Usuario usuario5 = new Usuario("franco", "pesce", "f@gmail.com");

        Membresia membresiaNueva = new Membresia(comunidad1, usuario1, new Rol());
        usuario1.unirseAComunidad(membresiaNueva);
        comunidad1.agregarMembresia(membresiaNueva);

        Membresia membresiaNueva2 = new Membresia(comunidad1, usuario2, new Rol());
        usuario2.unirseAComunidad(membresiaNueva2);
        comunidad1.agregarMembresia(membresiaNueva2);

        Membresia membresiaNueva3 = new Membresia(comunidad1, usuario3, new Rol());
        usuario3.unirseAComunidad(membresiaNueva3);
        comunidad1.agregarMembresia(membresiaNueva3);

        Membresia membresiaNueva4 = new Membresia(comunidad1, usuario4, new Rol());
        usuario4.unirseAComunidad(membresiaNueva4);
        comunidad1.agregarMembresia(membresiaNueva4);

        Membresia membresiaNueva5 = new Membresia(comunidad2, usuario1, new Rol());
        usuario1.unirseAComunidad(membresiaNueva5);
        comunidad2.agregarMembresia(membresiaNueva5);

        Membresia membresiaNueva6 = new Membresia(comunidad2, usuario2, new Rol());
        usuario2.unirseAComunidad(membresiaNueva6);
        comunidad2.agregarMembresia(membresiaNueva6);

        Membresia membresiaNueva7 = new Membresia(comunidad2, usuario3, new Rol());
        usuario3.unirseAComunidad(membresiaNueva7);
        comunidad2.agregarMembresia(membresiaNueva7);

        Membresia membresiaNueva8 = new Membresia(comunidad2, usuario5, new Rol());
        usuario5.unirseAComunidad(membresiaNueva8);
        comunidad2.agregarMembresia(membresiaNueva8);

        repoComunidad.agregar(comunidad1);
        repoComunidad.agregar(comunidad2);
        repoEstablecimiento.agregar(establecimiento1);
        repoEstablecimiento.agregar(establecimiento2);
        repoEstablecimiento.agregar(establecimiento3);
        repoEstablecimiento.agregar(establecimiento4);
        repoEstablecimiento.agregar(establecimiento5);
    }
}
