package models.repositorios;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.comunidades.Comunidad;
import models.incidentes.IncidenteDeComunidad;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import models.usuario.Usuario;

import java.util.List;
import java.util.Optional;

public class RepoUsuario extends RepoGenerico<Usuario> {

    public static RepoUsuario INSTANCE = new RepoUsuario();

    public RepoUsuario() {
        super(Usuario.class);
    }

    public List<Usuario> buscarPorNombre(String nombre) {
        return entityManager().createQuery("select e from Usuario e where e.nombre = :nombre", Usuario.class)
                .setParameter("nombre", nombre)
                .getResultList();
    }

    public List<Usuario> getUsuariosConInteresEnServicio(Servicio servicio){
        return entityManager().createQuery("select u from Usuario u join u.intereses i where i.servicio = :servicio", Usuario.class)
                .setParameter("servicio", servicio)
                .getResultList();
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return entityManager().createQuery("select e from Usuario e where e.correoElectronico = :email", Usuario.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }

}

