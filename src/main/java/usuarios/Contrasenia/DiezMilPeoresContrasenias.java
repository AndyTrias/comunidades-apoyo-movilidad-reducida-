package usuarios.Contrasenia;

import java.util.Arrays;

public class DiezMilPeoresContrasenias {


    private static String[] lista;

            // Sorteada para el binarySearch()

    public static boolean estaEnLaLista(String contrasenia){
        int resultado = Arrays.binarySearch(DiezMilPeoresContrasenias.lista, contrasenia);
        return resultado < 0; //si es < 0, el resultado no esta en la lista

    }
}

