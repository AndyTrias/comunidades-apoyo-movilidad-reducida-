@startuml

entity "Usuario" as eUsuario{
  id_usuario: number <<generated>>  
  --
  nombre : varchar(20)
  apellido : varchar(30)
  correo : varchar(50)
  contraseña : varchar(20)
  membresias : 
  interes : 
  localizaciones : 
  
}

entity "Interes" as eInteres{
  id_Servicio:FK
  id_Entidad:FK
}

entity "interes_servicio" as eInteres_servicio {
    id_interes_servicio:number <<generated>>
    --
    id_Servicio:FK
    id_Interes:FK
} 

entity "Servicio" as eServico{
  id_servicio:number <<generated>>
  --
  informacion:varchar(100)  
}



entity "Memebresia" as eMemebresia{
 id_comunidad : FK
 id_rol : FK
 afectaciones : FK
}

entity "Comunidad" as eComunidad{
  id_comunidad : number <<generated>>
  nombre : varchar(30)
  servicioDeInteres : FK
  incidentesAbiertos : FK
  incidentesCerrados : FK
  notificador:? 
}

entity "Rol" as eRol{
  id_rol : number <<generated>>
  usuario : FK
  permiso : FK  
  nombre : varchar(30)
}

entity "Permiso" as ePermiso{
  id_permiso : number <<generated>>
  nombre : varchar(30)
}

entity "Incidente" as eIncidente{
  id_incidente: number <<generated>> 
  abiertoPorUsuario:FK
  prestacionDeServicio:FK
  observaciones:varchar(100)
}

entity "Fecha_Incidente" as eFechaIncidente{
  id_fecha_incidente: number <<generated>>
  --
  id_incidente: FK
  fecha: DATETIME
} 

entity "Afectacion" as eAfectacion {
  id_afectacion: number <<generated>>
  afectado:boolean
  prestacionDeServicio:FK
}

entity "PrestacionDeServicio" as ePrestacionDeCervicio {
 id_prestacion_servicio: number <<generated>>
 servicio:FK
 incidentes:FK
 nombre:varchar(30)
} 

entity "Establecimiento" as eEstablecimiento {
  id_establecimiento: number <<generated>>
  localizacion:FK
  servicios:FK
}

entity "Entidad" as eEntidad {
  id_entidad: number <<generated>>
  Establecimientos:FK
  localizacion:FK
  nombre:varchar(30)
} 

entity "EntidadPrestadora" as eEntidadPrestadora {
  id_entidad_prestadora: number <<generated>>
  personaDesignada:FK
  serviciosPrestados:FK
}
 
eFechaIncidente |o--|| eIncidente
eComunidad ||--o{ eIncidente
eRol ||--o{ ePermiso
eUsuario||--o|eInteres
eMemebresia ||--||eRol
eRol ||--o{eUsuario
eMemebresia ||--||eComunidad
eComunidad ||--|{eRol
eInteres||--o{ eInteres_servicio
eInteres_servicio }o--|| eServico
@enduml     
