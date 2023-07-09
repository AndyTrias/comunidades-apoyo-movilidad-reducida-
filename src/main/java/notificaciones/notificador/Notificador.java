package notificaciones.notificador;

import comunidades.usuario.Usuario;
import comunidades.usuario.configuraciones.formas.FormaDeRecibir;
import notificaciones.Notificacion;

import java.util.List;

public interface Notificador {
    public void notificar(List<Usuario> usuarios);
}
