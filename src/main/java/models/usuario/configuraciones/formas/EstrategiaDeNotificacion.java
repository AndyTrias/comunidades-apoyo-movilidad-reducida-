package models.usuario.configuraciones.formas;

import models.notificaciones.Notificacion;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class EstrategiaDeNotificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public abstract void notificar(Notificacion notificacion);
}
