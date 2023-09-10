package usuario.configuraciones.medios.mail;

public interface IAdapterMail {
    void notificar(String email, String asunto, String cuerpo);
}
