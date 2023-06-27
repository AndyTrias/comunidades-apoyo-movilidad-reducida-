package comunidades.usuario.configNotificaciones;

import comunidades.usuario.Email;

public interface AdapterNotificarPorMail {
    public void notificar(Email email, String asunto, String cuerpo);
}
