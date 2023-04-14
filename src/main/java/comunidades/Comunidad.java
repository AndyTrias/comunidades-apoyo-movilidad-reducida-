package comunidades;
//Una comunidad es un conjunto de personas que comparten determinado interés en una problemática, en un conjunto de servicios o que, al conocerse entre ellas, deciden actuar colaborativamente. Las comunidades son un factor fundamental para este sistema ya que podrán compartir información, recomendaciones, etc.
//En las primeras versiones, no se prevé la colaboración entre diferentes comunidades. Las comunidades contarán con un conjunto de miembros, de los cuales se conoce su nombre, apellido y correo electrónico. Una persona física real puede ser miembro de diversas comunidades.
//Todas las comunidades cuentan con un conjunto de usuarios administradores que serán designados por el proveedor de la plataforma tras la validación de que no tienen conflictos de intereses con las empresas prestadoras de servicios o que podrían intervenir con información tendenciosamente y maliciosa a favor de los prestadores u otros interesados.


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter
public class Comunidad {

    private ArrayList<Persona> integrantes;

    //mas adelante agregar una lista de comunidades para que se conozcon entre soi





}


