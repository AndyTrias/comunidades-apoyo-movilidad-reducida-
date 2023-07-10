package comunidades.usuario.configuraciones.medios.mail;

import comunidades.usuario.Email;

public interface IAdapterMail {
    void notificar(Email email, String asunto, String cuerpo);
}
