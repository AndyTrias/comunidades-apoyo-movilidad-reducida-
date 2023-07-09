package comunidades.usuario.configuraciones.medios.mail;

import comunidades.usuario.Email;

public interface IAdapterMail {
    public void notificar(Email email, String asunto, String cuerpo);
}
