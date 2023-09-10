package repositiorios;

import servicios.Servicio;
import usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RepoUsuarios {
    private static RepoUsuarios instance = null;
    private List<Usuario> usuarios;

    private RepoUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    public static RepoUsuarios getInstance() {
        if (instance == null) {
            instance = new RepoUsuarios();
        }
        return instance;
    }

    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
    }

    public List<Usuario> getUsuariosConInteresEnServicio(Servicio servicio) {
        return this.usuarios.stream().filter(usuario -> usuario.getInteres().getServicios().contains(servicio)).toList();
    }

    public void borrarUsuarios() {
        this.usuarios.clear();
    }

}
