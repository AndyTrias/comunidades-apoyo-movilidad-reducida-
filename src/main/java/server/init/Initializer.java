package server.init;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.comunidades.Permiso;
import models.comunidades.Rol;
import models.comunidades.TipoRol;
import models.repositorios.RepoRol;

public class Initializer implements WithSimplePersistenceUnit {

  public static void init() {
    if (new RepoRol().buscarTodos().size() == 0) {
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
        {"Afectar Prestacion", "afectar_prestaciones"}
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

    Rol prestador = new Rol();
    prestador.setNombre("Administrador de Comunidad");
    prestador.setTipoRol(TipoRol.ADMINISTRADOR_COMUNIDAD);
    prestador.agregarPermisos(
        buscadorDePermisos.buscarPermisoPorNombre("agregar_servicio_de_interes")
    );
    entityManager().persist(prestador);

    return this;
  }
}
