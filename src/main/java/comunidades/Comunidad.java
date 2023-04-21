package comunidades;


import lombok.Getter;
import lombok.Setter;
import usuarios.Usuario;

import java.util.ArrayList;

@Setter @Getter
public class Comunidad {

    private ArrayList<Persona> integrantes;
    private ArrayList<Usuario> administradores;

    //mas adelante agregar una lista de comunidades para que se conozcon entre si

    public void agregarPersonas(Persona personas){
         this.integrantes.add(personas);
    }



}
