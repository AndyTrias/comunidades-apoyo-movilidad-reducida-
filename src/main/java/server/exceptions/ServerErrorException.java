package server.exceptions;

public class ServerErrorException extends RuntimeException{
  public ServerErrorException(String mensaje){
    super(mensaje);
  }
}
