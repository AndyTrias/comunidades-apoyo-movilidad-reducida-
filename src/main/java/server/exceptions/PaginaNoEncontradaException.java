package server.exceptions;

public class PaginaNoEncontradaException extends RuntimeException{
  public PaginaNoEncontradaException(String mensaje){
    super(mensaje);
  }
}
