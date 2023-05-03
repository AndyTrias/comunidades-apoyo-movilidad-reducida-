import comunidades.Comunidad;
import usuarios.Contrasenia.DiezMilPeoresContrasenias;
import usuarios.Contrasenia.ValidadorDeContrasenia;
import usuarios.Contrasenia.ValidarLongitud;
import usuarios.Usuario;

public class Main {
    public static void main(String[]args) throws Exception {
        Usuario franco = new Usuario("Franco", "Pesce", "francopescee@gmail.com");
        Usuario andy = new Usuario("Andres", "Trias", "andytrias@gmail.com");

        Comunidad comunidad = new Comunidad("Comunidad de Programadores");

        franco.unirseAComunidad(comunidad);
        comunidad.agregarAdministrador(andy);

        comunidad.getMiembros().forEach(miembro -> {
            System.out.println(miembro.getUsuario().getNombre());
        });

        comunidad.getAdministradores().forEach(administrador -> {
            System.out.println(administrador.getUsuario().getNombre());
        });
    }
}
