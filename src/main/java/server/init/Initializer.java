package server.init;

import controllers.InformesController;
import controllers.factories.FactoryController;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.usuario.Permiso;
import models.usuario.Rol;
import models.usuario.TipoRol;
import models.repositorios.RepoRol;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Initializer implements WithSimplePersistenceUnit {

  public static void init() {
    if (new RepoRol().buscarTodos().size() < 4) {
      new Initializer()
          .iniciarTransaccion()
          .permisos()
          .roles()
          .commitearTransaccion();

    }
  }

  private Initializer iniciarTransaccion() {
    entityManager().getTransaction().begin();
    return this;
  }

  private void commitearTransaccion() {
    entityManager().getTransaction().commit();
  }

  private Initializer permisos() {
    String[][] permisos = {
        {"Crear Establecimiento", "crear_establecimiento"},
        {"Crear servicio", "crear_servicio"},
        {"Crear Entidad", "crear_entidad"},
        {"Crear Prestacion", "crear_prestacion"},
        {"Agregar Servicio de Interes", "agregar_servicio_de_interes"},
        {"Afectar Prestacion", "afectar_prestaciones"},
        {"Ver ranking de Organismos", "ver ranking de organismos"},
        {"Ver ranking de Entidades", "ver ranking de entidades"}
    };

    for (String[] unPermiso : permisos) {
      Permiso permiso = new Permiso();
      permiso.setNombre(unPermiso[0]);
      permiso.setNombreInterno(unPermiso[1]);
      entityManager().persist(permiso);
    }

    return this;
  }

  private interface BuscadorDePermisos extends WithSimplePersistenceUnit {
    default Permiso buscarPermisoPorNombre(String nombre) {
      return (Permiso) entityManager()
          .createQuery("from Permiso where nombreInterno = :nombre")
          .setParameter("nombre", nombre)
          .getSingleResult();
    }
  }

  private Initializer roles() {
    BuscadorDePermisos buscadorDePermisos = new BuscadorDePermisos() {
    };

    Rol administrador = new Rol();
    administrador.setNombre("Administrador de Plataforma");
    administrador.setTipoRol(TipoRol.ADMINISTRADOR_PLATAFORMA);
    administrador.agregarPermisos(
        buscadorDePermisos.buscarPermisoPorNombre("crear_servicio"),
        buscadorDePermisos.buscarPermisoPorNombre("crear_entidad"),
        buscadorDePermisos.buscarPermisoPorNombre("crear_establecimiento"),
        buscadorDePermisos.buscarPermisoPorNombre("crear_prestacion")
    );
    entityManager().persist(administrador);

    Rol miembro = new Rol();
    miembro.setNombre("Miembro");
    miembro.setTipoRol(TipoRol.MIEMBRO);
    miembro.agregarPermisos(
        buscadorDePermisos.buscarPermisoPorNombre("afectar_prestaciones"));
    entityManager().persist(miembro);

    Rol adminComunidad = new Rol();
    adminComunidad.setNombre("Administrador de Comunidad");
    adminComunidad.setTipoRol(TipoRol.ADMINISTRADOR_COMUNIDAD);
    adminComunidad.agregarPermisos(
        buscadorDePermisos.buscarPermisoPorNombre("agregar_servicio_de_interes")
    );
    entityManager().persist(adminComunidad);

    Rol organismo = new Rol();
    organismo.setNombre("Organismo de Control");
    organismo.setTipoRol(TipoRol.ORGANISMO_DE_CONTROL);
    organismo.agregarPermisos(
        buscadorDePermisos.buscarPermisoPorNombre("crear_entidad"),
        buscadorDePermisos.buscarPermisoPorNombre("crear_establecimiento"),
        buscadorDePermisos.buscarPermisoPorNombre("ver ranking de organismos"),
        buscadorDePermisos.buscarPermisoPorNombre("ver ranking de entidades")
    );
    entityManager().persist(organismo);


    Rol entidad = new Rol();
    entidad.setNombre("Entidad Prestadora");
    entidad.setTipoRol(TipoRol.ENTIDAD_PRESTADORA);
    entidad.agregarPermisos(
        buscadorDePermisos.buscarPermisoPorNombre("crear_entidad"),
        buscadorDePermisos.buscarPermisoPorNombre("crear_establecimiento"),
        buscadorDePermisos.buscarPermisoPorNombre("ver ranking de entidades")
    );
    entityManager().persist(entidad);
    return this;

  }

  private void activarProcesos() {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    executor.scheduleWithFixedDelay(() -> {
      InformesController informesController = (InformesController) FactoryController.controller("Informe");
      informesController.generarRankings();
      System.out.println("Tarea programada ejecutada");
    }, 7, 7, TimeUnit.DAYS);
  }


}
