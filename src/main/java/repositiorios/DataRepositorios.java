package repositiorios;

import comunidades.Comunidad;
import comunidades.usuario.Email;
import comunidades.usuario.Usuario;
import entidades.Entidad;
import entidades.Establecimiento;
import localizacion.Localizacion;
import localizacion.UbicacionExacta;
import lombok.Setter;
import servicios.PrestacionDeServicio;
import servicios.Servicio;

public class DataRepositorios {
    public RepoComunidades repoComunidades;
    public RepoEntidades repoEntidades;
    public RepoUsuarios repoUsuarios;

    public DataRepositorios() {
        this.repoComunidades = RepoComunidades.getInstance();
        this.repoEntidades = RepoEntidades.getInstance();
        this.repoUsuarios = RepoUsuarios.getInstance();

        this.AsignarComunidades();
        this.AsignarEntidades();
        this.AsignarUsuarios();
    }

    private void AsignarComunidades(){
        Servicio servicio = new Servicio("baño hombres");
        PrestacionDeServicio banioMedrano = new PrestacionDeServicio(servicio, "baño Medrano", new UbicacionExacta(1, 1));
        PrestacionDeServicio banioCastroBarros= new PrestacionDeServicio(servicio, "baño Castro Barros", new UbicacionExacta(2, 2));

        // Creamos las 3 comunidades
        Comunidad comunidad1 = new Comunidad("comunidad1");
        comunidad1.agregarServicioDeInteres(banioMedrano);
        Comunidad comunidad2 = new Comunidad("comunidad2");
        comunidad2.agregarServicioDeInteres(banioMedrano);
        Comunidad comunidad3 = new Comunidad("comunidad3");
        comunidad3.agregarServicioDeInteres(banioMedrano);
        comunidad3.agregarServicioDeInteres(banioCastroBarros);

        this.repoComunidades.agregarComunidad(comunidad1);
        this.repoComunidades.agregarComunidad(comunidad2);
        this.repoComunidades.agregarComunidad(comunidad3);
    }

    private void AsignarEntidades(){
        Entidad entidad = new Entidad("Santander Rio Argentina");
        Establecimiento establecimiento1 = new Establecimiento("Sucursal Almagro", new Localizacion());
        Establecimiento establecimiento2 = new Establecimiento("Sucursal Buenos Aires", new Localizacion());

        entidad.agregarEstablecimiento(establecimiento1);
        entidad.agregarEstablecimiento(establecimiento2);

        this.repoEntidades.agregarEntidad(entidad);
    }

    private void AsignarUsuarios(){
        Email emailfranco = new Email();
        Email emailfede = new Email();

        emailfranco.nombreDeUsuario = "francopescee";
        emailfranco.dominio = "gmail.com";

        emailfede.nombreDeUsuario = "tandres";
        emailfede.dominio = "frba.utn.edu.ar";

        // Creamos los 2 usuarios
        Usuario franco = new Usuario("franco", "pesce", emailfranco);
        Usuario fede = new Usuario("fede", "perez", emailfede);

        Comunidad comunidad1 = repoComunidades.getComunidades().get(0);
        Comunidad comunidad2 = repoComunidades.getComunidades().get(1);
        Comunidad comunidad3 = repoComunidades.getComunidades().get(2);

        // agregamos los usuarios a las comunidades
        franco.unirseAComunidad(comunidad1, comunidad1.aceptarUsuario(franco));
        franco.unirseAComunidad(comunidad2, comunidad2.aceptarUsuario(franco));
        fede.unirseAComunidad(comunidad2, comunidad2.aceptarUsuario(fede));
        fede.unirseAComunidad(comunidad3, comunidad3.aceptarUsuario(fede));
    }
}
