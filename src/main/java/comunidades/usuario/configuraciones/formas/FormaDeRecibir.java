package comunidades.usuario.configuraciones.formas;

import comunidades.usuario.configuraciones.medios.MedioPreferido;
import lombok.Setter;
import notificaciones.Notificacion;

public abstract class FormaDeRecibir {
    @Setter protected MedioPreferido medioPreferido;
    public abstract void notificar(Notificacion notificacion);
}
