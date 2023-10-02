package models.repositorios;

import models.servicios.Servicio;
import models.usuario.Usuario;

import java.util.List;

public class RepoUsuario extends RepoGenerico<Usuario> {
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
}
