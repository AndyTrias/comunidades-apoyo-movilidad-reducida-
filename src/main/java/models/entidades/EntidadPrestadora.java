package models.entidades;

import models.entidades.enviadorDeInformacion.AdapterEnviadorDeInformacion;
import models.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
@Table(name = "entidad_prestadora")
public class EntidadPrestadora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Getter
    @Column(name = "nombre")
    private String nombre;

    @Setter
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) 
    private Usuario personaDesignada;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "entidad_prestadora_id")
    private List<Entidad> serviciosPrestados;

    @Transient
    private AdapterEnviadorDeInformacion enviadorDeInformacion;

    public EntidadPrestadora(String nombre){
        this.nombre = nombre;
        this.serviciosPrestados = new ArrayList<>();
    }

    public EntidadPrestadora() {

    }

    public void enviarInformacion(){
        enviadorDeInformacion.enviarInformacion();
    }

    public void agregarEntidad(Entidad entidad){
        serviciosPrestados.add(entidad);
    }
}
