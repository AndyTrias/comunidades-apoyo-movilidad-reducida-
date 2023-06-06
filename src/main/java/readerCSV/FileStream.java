package readerCSV;

import entidades.OrganismoDeControl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
public abstract class FileStream {
    public void leerCSV(String ruta){
        List<String[]> csvComoLista = csvALista(ruta);
        levantarObjetos(csvComoLista);
    }


    public List<String[]> csvALista(String ruta) {
        try (Stream<String> streamFile = Files.lines(Paths.get(ruta))) {
            return streamFile.map(line -> line.split(";")).toList();
        } catch (IOException csv) {
            return null;
        }
    }

    public abstract void levantarObjetos(List<String[]> csvComoLista);

}
