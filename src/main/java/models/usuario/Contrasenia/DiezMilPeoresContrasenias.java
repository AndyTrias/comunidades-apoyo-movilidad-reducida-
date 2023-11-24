package models.usuario.Contrasenia;
import models.configs.Config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class DiezMilPeoresContrasenias implements PuedeValidar {

    private static final String path = Config.getInstance().PEORES_CONTRASENIAS; //ordenado para la busqueda


    public boolean validar(String contrasenia) {
        try (BufferedReader br = new BufferedReader(new FileReader(DiezMilPeoresContrasenias.path))) {
            String line;
            while ((line = br.readLine()) != null) {
                int cmp = line.compareTo(contrasenia);
                if (cmp == 0) {
                    // encontro la palabra
                    return false;
                } else if (cmp > 0) { //el true y false estan invertidos para conservar que si devuelve true es que paso la validacion
                    // ya paso por el lugar en el que tendria que estar, por lo que se deja de buscar
                    return true;
                }
            }
            // termino el archivo y no la encontro todavia
            return false;
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
            return false;
        }
    }
}

