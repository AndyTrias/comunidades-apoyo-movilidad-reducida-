package readerCSV;

import comunidades.Usuario;
import entidades.Entidad;
import localizacion.Localizacion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class fileStream {

<<<<<<< HEAD
    public static void main(String[] args) {
        String filePath = "src/main/java/readerCSV/Instancias.csv";
        List<Entidad> entidades = new ArrayList<>();

        try(Stream<String> streamFile = Files.lines(Paths.get(filePath))){
             entidades = streamFile.map(Line -> Line.split(",")).map(arreglo ->{
                Entidad ent= new Entidad(arreglo[0]);
                return ent;
            }).collect(Collectors.toList());

            entidades.forEach(entidad -> System.out.println(entidad.getNombre()));
        }catch (IOException csv){
            System.err.println("Error al leer el archivo");
=======
    private String filePath = "src/main/java/readerCSV/Instancias.csv";
    private List<Entidad> entidadades = new ArrayList<>();

    public List<String> levantarCSV() {
        try (Stream<String> streamFile = Files.lines(Paths.get(filePath))) {
            entidadades = streamFile.map(Line -> Line.split(",")).map(arreglo -> {
                return new Entidad(arreglo[0]);
            }).collect(Collectors.toList());

            return streamFile.collect(Collectors.toList());
        } catch (IOException csv) {
            return null;
>>>>>>> 8537d424beffb7426b0ae7e76b787cc8daf12480
        }
    }
}
