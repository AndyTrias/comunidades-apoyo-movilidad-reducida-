package comunidades.usuario.configuraciones.medios.mail;

import comunidades.usuario.Usuario;
import comunidades.usuario.configuraciones.medios.MedioPreferido;
import notificaciones.Notificacion;

public class NotificarPorMail implements MedioPreferido {
    private IAdapterMail adapter;

    public void notificar(Usuario usuario, Notificacion notificacion) {
        adapter.notificar(usuario.getCorreoElectronico(), notificacion.getAsunto(), notificacion.getCuerpo());
    }
}
