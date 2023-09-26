package models.entidades;

import lombok.Setter;
import models.entidades.enviadorDeInformacion.AdapterEnviadorDeInformacion;
import models.usuario.Usuario;
import lombok.Getter;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "organismo_de_control")
public class OrganismoDeControl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "nombre") 
    private String nombre;

    @Setter
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Usuario personaDesignada;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "organismo_de_control_id")
    private List<EntidadPrestadora> entidadesQuePosee;

    @Transient
    private AdapterEnviadorDeInformacion enviadorDeInformacion;

    public OrganismoDeControl(String nombre){
        this.nombre = nombre;
    }

    public OrganismoDeControl() {}

    public void enviarInformacion(){
        enviadorDeInformacion.enviarInformacion();
    }

    public void agregarEntidad(EntidadPrestadora entidad){
        entidadesQuePosee.add(entidad);
    }
}
