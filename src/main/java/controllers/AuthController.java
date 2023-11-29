package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.usuario.TipoRol;
import models.entidades.EntidadPrestadora;
import models.entidades.OrganismoDeControl;
import models.repositorios.RepoEntidadPrestadora;
import models.repositorios.RepoOrganismoDeControl;
import models.repositorios.RepoRol;
import models.repositorios.RepoUsuario;
import models.usuario.Usuario;
import server.exceptions.CredencialesInvalidaException;
import server.exceptions.EntidadNoExistenteException;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class AuthController {
  private RepoUsuario repoUsuario;
  private RepoRol repoRol;
  private RepoEntidadPrestadora repoEntidadPrestadora;
  private RepoOrganismoDeControl repoOrganismoDeControl;

  public void showLogin(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    model.put("notlogged", true);
    ctx.render("auth/login.hbs", model);

  }

  public void showRegister(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    model.put("notlogged", true);
    ctx.render("auth/login.hbs", model);
  }

  public void showAdmin(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    model.put("administrador", true);
    model.put("organismos", repoOrganismoDeControl.buscarTodos());
    model.put("prestadoras", repoEntidadPrestadora.buscarTodos());
    ctx.render("admin/usuarios.hbs", model);
  }

  public void login(Context ctx) {
    String email = ctx.formParam("email");
    String password = ctx.formParam("password");

    Usuario usuario = repoUsuario.buscarPorEmail(email).orElse(null);

    if (usuario == null || !usuario.getContrasenia().equals(password)) {
      throw new CredencialesInvalidaException("Usuario o contrasenia Incorrectos");
    }

    loguear_atributos(ctx, usuario);
  }

  public void logout(Context ctx) {
    ctx.consumeSessionAttribute("usuario_id");
    ctx.consumeSessionAttribute("tipo_rol");
    ctx.redirect("/login");
  }


  public void register(Context ctx) {
    String nombre = ctx.formParam("nombre");
    String apellido = ctx.formParam("apellido");
    String email = ctx.formParam("email");
    String password = ctx.formParam("password");
    String telefono = ctx.formParam("telefono");


    if (repoUsuario.buscarPorEmail(email).orElse(null) != null) {
      throw new CredencialesInvalidaException("Ya existe un usuario con ese email");
    }

    Usuario usuario = new Usuario(nombre, apellido, email);
    usuario.setTelefono(telefono);
    usuario.setContrasenia(password);
    usuario.setRol(repoRol.buscarPorNombre(TipoRol.MIEMBRO));
    repoUsuario.agregar(usuario);

    loguear_atributos(ctx, usuario);


  }

  public void registerEntidad(Context ctx) {
    String email = ctx.formParam("email");
    String password = ctx.formParam("password");
    String prestadora = ctx.formParams("prestadora").get(1);

    if (repoUsuario.buscarPorEmail(email).orElse(null) != null) {
      throw new CredencialesInvalidaException("Ya existe un usuario con ese email");
    }

    EntidadPrestadora entidadPrestadora = repoEntidadPrestadora.buscar(Long.valueOf(prestadora));

    if (entidadPrestadora == null) {
      throw new EntidadNoExistenteException("No existe esa entidad");
    }

    Usuario usuario = new Usuario(entidadPrestadora.getNombre(), "", email);
    usuario.setContrasenia(password);
    usuario.setRol(repoRol.buscarPorNombre(TipoRol.ENTIDAD_PRESTADORA));
    entidadPrestadora.setPersonaDesignada(usuario);
    repoEntidadPrestadora.modificar(entidadPrestadora);
    ctx.redirect("/admin/usuarios");
  }


    public void registerOrganismo(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String organismo = ctx.formParams("organismo").get(1);

        if (repoUsuario.buscarPorEmail(email).orElse(null) != null) {
            throw new CredencialesInvalidaException("Ya existe un usuario con ese email");
        }

        OrganismoDeControl organismoDeControl = repoOrganismoDeControl.buscar(Long.valueOf(organismo));

        if (organismoDeControl == null) {
            throw new EntidadNoExistenteException("No existe ese organimo");
        }

        Usuario usuario = new Usuario(organismoDeControl.getNombre(), "Organismo", email);
        usuario.setContrasenia(password);
        usuario.setRol(repoRol.buscarPorNombre(TipoRol.ORGANISMO_DE_CONTROL));

        organismoDeControl.setPersonaDesignada(usuario);
        repoOrganismoDeControl.modificar(organismoDeControl);
        ctx.redirect("/admin/usuarios");
    }

  public void loguear_atributos(Context ctx, Usuario usuario) {
    ctx.sessionAttribute("tipo_rol", usuario.getTipoRol().toString());
    ctx.sessionAttribute("usuario_id", usuario.getId().toString());
    ctx.redirect("/");


  }
}
