import noServer.entidades.EntidadPrestadora;
import noServer.entidades.OrganismoDeControl;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import noServer.repositiorios.RepoEntidadPrestadora;
import noServer.repositiorios.RepoOrganismoDeControl;

import javax.persistence.EntityTransaction;

public class Main implements SimplePersistenceTest {

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();

        RepoOrganismoDeControl repoOrganismoDeControl = new RepoOrganismoDeControl();
        RepoEntidadPrestadora repoEntidadPrestadora = new RepoEntidadPrestadora();
        OrganismoDeControl o = repoOrganismoDeControl.buscar(1L);
        EntidadPrestadora e = repoEntidadPrestadora.buscar(2L);
        o.agregarEntidad(e);
        repoOrganismoDeControl.modificar(o);
    }
}
