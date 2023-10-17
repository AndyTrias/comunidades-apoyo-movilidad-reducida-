package server.exceptions;

public class EntidadNoExistenteException extends RuntimeException {
    public EntidadNoExistenteException(String message) {
        super(message);
    }
}
