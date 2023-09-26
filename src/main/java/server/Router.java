package server;

public class Router {
  public static void init() {

    Server.app().get("/", ctx -> {
      ctx.result("Hello World");
    });
  }
}
