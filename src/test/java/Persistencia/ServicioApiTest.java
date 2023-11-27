/*
package Persistencia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.comunidades.Comunidad;
import models.comunidades.Membresia;
import models.usuario.Rol;
import models.entidades.Establecimiento;
import models.external.retrofit.apiServicio3.ApiServicio3;
import models.external.retrofit.apiServicio3.responseClases.EntidadDTO;
import models.external.retrofit.apiServicio3.responseClases.IncidenteDTO;
import models.external.retrofit.apiServicio3.responseClases.PayloadServicio3DTO;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoEstablecimiento;
import models.servicios.PrestacionDeServicio;
import models.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    public void datosParaProbarNuestraApi() throws JsonProcessingException {
        IncidenteDTO incidenteDTO = new IncidenteDTO();
        incidenteDTO.setFechaApertura("2023-09-01");
        incidenteDTO.setFechaCierre("2023-09-16");
        incidenteDTO.setMiembrosAfectados(47);

        IncidenteDTO incidenteDTO2 = new IncidenteDTO();
        incidenteDTO2.setFechaApertura("2023-08-15");
        incidenteDTO2.setFechaCierre("2023-08-25");
        incidenteDTO2.setMiembrosAfectados(32);

        IncidenteDTO incidenteDTO3 = new IncidenteDTO();
        incidenteDTO3.setFechaApertura("2023-07-10");
        incidenteDTO3.setFechaCierre("2023-07-21");
        incidenteDTO3.setMiembrosAfectados(22);

        EntidadDTO entidadDTO = new EntidadDTO();
        entidadDTO.setId(1);
        entidadDTO.getIncidentes().add(incidenteDTO);
        entidadDTO.getIncidentes().add(incidenteDTO2);
        entidadDTO.getIncidentes().add(incidenteDTO3);

        IncidenteDTO incidenteDTO4 = new IncidenteDTO();
        incidenteDTO4.setFechaApertura("2023-09-03");
        incidenteDTO4.setFechaCierre("2023-09-09");
        incidenteDTO4.setMiembrosAfectados(12);

        IncidenteDTO incidenteDTO5 = new IncidenteDTO();
        incidenteDTO5.setFechaApertura("2023-08-18");
        incidenteDTO5.setFechaCierre("2023-08-23");
        incidenteDTO5.setMiembrosAfectados(8);

        EntidadDTO entidadDTO2 = new EntidadDTO();
        entidadDTO2.setId(2);
        entidadDTO2.getIncidentes().add(incidenteDTO4);
        entidadDTO2.getIncidentes().add(incidenteDTO5);

        IncidenteDTO incidenteDTO6 = new IncidenteDTO();
        incidenteDTO6.setFechaApertura("2023-08-25");
        incidenteDTO6.setFechaCierre("2023-09-18");
        incidenteDTO6.setMiembrosAfectados(1);

        IncidenteDTO incidenteDTO7 = new IncidenteDTO();
        incidenteDTO7.setFechaApertura("2023-07-30");
        incidenteDTO7.setFechaCierre("2023-08-05");
        incidenteDTO7.setMiembrosAfectados(1);

        EntidadDTO entidadDTO3 = new EntidadDTO();
        entidadDTO3.setId(3);
        entidadDTO3.getIncidentes().add(incidenteDTO6);
        entidadDTO3.getIncidentes().add(incidenteDTO7);

        IncidenteDTO incidenteDTO8 = new IncidenteDTO();
        incidenteDTO8.setFechaApertura("2023-08-10");
        incidenteDTO8.setFechaCierre("2023-08-19");
        incidenteDTO8.setMiembrosAfectados(34);

        IncidenteDTO incidenteDTO9 = new IncidenteDTO();
        incidenteDTO9.setFechaApertura("2023-07-05");
        incidenteDTO9.setFechaCierre("2023-07-15");
        incidenteDTO9.setMiembrosAfectados(19);

        EntidadDTO entidadDTO4 = new EntidadDTO();
        entidadDTO4.setId(4);
        entidadDTO4.getIncidentes().add(incidenteDTO8);
        entidadDTO4.getIncidentes().add(incidenteDTO9);

        IncidenteDTO incidenteDTO10 = new IncidenteDTO();
        incidenteDTO10.setFechaApertura("2023-07-20");
        incidenteDTO10.setFechaCierre("2023-07-27");
        incidenteDTO10.setMiembrosAfectados(63);

        IncidenteDTO incidenteDTO11 = new IncidenteDTO();
        incidenteDTO11.setFechaApertura("2023-06-25");
        incidenteDTO11.setFechaCierre("2023-07-03");
        incidenteDTO11.setMiembrosAfectados(28);

        EntidadDTO entidadDTO5 = new EntidadDTO();
        entidadDTO5.setId(5);
        entidadDTO5.getIncidentes().add(incidenteDTO10);
        entidadDTO5.getIncidentes().add(incidenteDTO11);

        IncidenteDTO incidenteDTO12 = new IncidenteDTO();
        incidenteDTO12.setFechaApertura("2023-06-15");
        incidenteDTO12.setFechaCierre("2023-06-25");
        incidenteDTO12.setMiembrosAfectados(17);

        IncidenteDTO incidenteDTO13 = new IncidenteDTO();
        incidenteDTO13.setFechaApertura("2023-05-28");
        incidenteDTO13.setFechaCierre("2023-06-05");
        incidenteDTO13.setMiembrosAfectados(11);

        EntidadDTO entidadDTO6 = new EntidadDTO();
        entidadDTO6.setId(6);
        entidadDTO6.getIncidentes().add(incidenteDTO12);
        entidadDTO6.getIncidentes().add(incidenteDTO13);

        List<EntidadDTO> entidadesDTO = List.of(entidadDTO, entidadDTO2, entidadDTO3, entidadDTO4, entidadDTO5, entidadDTO6);
        PayloadServicio3DTO payloadServicio3DTO = new PayloadServicio3DTO(entidadesDTO);

        try {
            PayloadServicio3DTO response = ApiServicio3.getInstancia().rankingEntidades(payloadServicio3DTO);
            System.out.println(new ObjectMapper().writeValueAsString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
*/
