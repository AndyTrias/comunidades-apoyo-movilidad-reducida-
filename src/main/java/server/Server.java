package server;

import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;

import java.util.function.Consumer;

public class Server {
  private static Javalin app = null;

  public static Javalin app() {
    if(app == null)
      throw new RuntimeException("App no inicializada");
    return app;
  }

  public static void init() {
    if(app == null) {
      int port = Integer.parseInt(System.getProperty("port", "8080"));
      app = Javalin.create(config()).start(port);
//      initTemplateEngine();
      Router.init();
    }
  }

  private static Consumer<JavalinConfig> config() {
    return config -> {
      config.staticFiles.add(staticFiles -> {
        staticFiles.hostedPath = "/";
        staticFiles.directory = "/public";
      });
    };
  }

}
