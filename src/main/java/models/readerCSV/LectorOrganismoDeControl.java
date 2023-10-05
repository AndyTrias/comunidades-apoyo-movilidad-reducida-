package models.readerCSV;

import models.entidades.OrganismoDeControl;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LectorOrganismoDeControl extends FileStream {

    @Getter
    private Set<OrganismoDeControl> organismosLeidos;

    public LectorOrganismoDeControl() {
        this.organismosLeidos = new HashSet<>();
    }

    protected void levantarObjetos(List<String[]> csvComoLista) {
        for (int i = 1; i < csvComoLista.size(); i++) {
            OrganismoDeControl organismoDeControl = new OrganismoDeControl(csvComoLista.get(i)[0]);
            organismosLeidos.add(organismoDeControl);
        }
    }


}
