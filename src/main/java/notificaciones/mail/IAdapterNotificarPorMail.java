package notificaciones.mail;

import comunidades.usuario.Email;

public interface IAdapterNotificarPorMail {
    public void notificar(Email email, String asunto, String cuerpo);
}
