package usuarios.Contrasenia;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class DiezMilPeoresContrasenias {

    private static String path = "10000Peores.txt"; //ordenado para la busqueda



    public static boolean estaEnLaLista(String contrasenia) {
        try (BufferedReader br = new BufferedReader(new FileReader(DiezMilPeoresContrasenias.path))) {
            String line;
            while ((line = br.readLine()) != null) {
                int cmp = line.compareTo(contrasenia);
                if (cmp == 0) {
                    // encontro la palabra
                    return true;
                } else if (cmp > 0) {
                    // ya paso por el lugar en el que tendria que estar, por lo que se deja de buscar
                    return false;
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

