package controllers.factories;

import controllers.IncidenteDeComunidadController;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoPrestacion;

public class FactoryController {
    public static Object controller(String nombre) {
        Object controller = null;
        switch (nombre) {
            case "Incidente de comunidad": controller = new IncidenteDeComunidadController(
                    new RepoComunidad(),
                    new RepoPrestacion()
            ); break;
        }
        return controller;
    }
}
