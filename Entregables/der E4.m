@startuml

entity "Usuario" as eUsuario{
  id_usuario: number <<generated>>  
  --
  intereses: FK
  localizaciones:FK
  ubicacionExacta:FK
  nombre : varchar(20)
  apellido : varchar(30)
  correo : varchar(50)
  contraseña : varchar(20)
  telefono: varchar(20)
  
}

entity "configuracion_de_notificaciones" as eConfigNoti{
id_configuracionNoti: number <<generated>>  
--
estrategiaDeNotificacion: varchar(255)
medioPreferido: varchar(255)

} 


entity "Interes" as eInteres{
  id_interes: number <<generated>>
  id_Servicio:FK
  id_Entidad:FK
  id_usuario:FK
}

entity "Servicio" as eServicio 
{
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

entity "incidente_abierto" as eIncidenteAbierto{
comunidad_id:FK
incidente_id:FK
}

entity "incidentes_cerrado" as eIncidenteCerrado{
comunidad_id:FK
incidente_id:FK
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
  -- 
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

entity "PrestacionDeServicio" as ePrestacionDeServicio {
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




entity "Provincia" as eProvincia {
  id_provincia: number <<generated>>
  --
  nombre:varchar(30)
}

entity "Municipio" as eMunicipio {
  id_municipio: number <<generated>>
  --
  provincia_id:FK
  nombre:varchar(30)
}

entity "Localidad" as eLocalidad {
  id_localidad: number <<generated>>
  --
  municipio_id:FK
  provincia_id:FK
  nombre:varchar(30)
}

entity "Localizacion" as eLocalizacion {
  id_localizacion: number <<generated>>
  --
  id_provincia:FK
  id_municipio:FK
  id_localidad:FK
  id_usuario:FK

}

entity "prestacion_comunidad" as ePrestacion_Comunidad{
  id_prestacion_servicio: PK, FK
  id_comunidad: PK, FK
}

entity "prestacion_incidente" as ePrestacion_Incidente{
  id_prestacion_servicio: PK, FK
  id_incidente: PK, FK
}

entity "Organismo de Control" as eOrganismo{
  id_organismo: number <<generated>>
  --
  id_usuario: FK
  id_entidad_prestadora: FK
}

entity "UbicacionExacta" as eUbicacionExacta {
  id_ubicacion_exacta: number <<generated>>
  --
  latitud: int
  longitud: int
}

entity "organismo_prestadora" as eOrganismo_Prestadora {
  id_organismo:PK, FK
  id_entidad_prestadora: PK, FK
}

ePrestacionDeServicio ||--|| eUbicacionExacta
eUsuario ||--o|eUbicacionExacta
eLocalizacion ||--|| eEntidad
eLocalizacion ||--|| eEstablecimiento
eUsuario ||--o{ eLocalizacion
eProvincia ||--|{ eLocalizacion 
eMunicipio ||--|{ eLocalizacion 
eLocalidad ||--|{ eLocalizacion 
eFechaIncidente |o--|| eIncidente
eComunidad ||--o{ eIncidente
eRol ||--o{ ePermiso
eUsuario||--o{eInteres
eMemebresia ||--||eRol
eRol ||--o{eUsuario
eMemebresia ||--||eComunidad
eComunidad ||--|{eRol
eMemebresia ||---o{ eAfectacion
eEntidadPrestadora ||--|| eUsuario
eEntidadPrestadora ||--|{ eEntidad
eEntidad ||--|{ eEstablecimiento
ePrestacionDeServicio ||--|| eServicio 
eAfectacion }|--|| ePrestacionDeServicio
eEstablecimiento ||--|{ ePrestacionDeServicio
ePrestacionDeServicio ||--|{ ePrestacion_Comunidad
eComunidad ||--|{ ePrestacion_Comunidad
ePrestacionDeServicio ||---|{ ePrestacion_Incidente
eIncidente ||---|{ ePrestacion_Incidente
eOrganismo ||-|| eUsuario
eOrganismo ||--|{ eOrganismo_Prestadora
eEntidadPrestadora ||----|{ eOrganismo_Prestadora
eIncidenteAbierto }|---|| eComunidad
eIncidenteAbierto }|---|| eIncidente
eIncidenteCerrado }|---|| eComunidad
eIncidenteCerrado }|---|| eIncidente
eProvincia ||----|{ eMunicipio
eLocalidad }|---|| eMunicipio
eLocalidad }|---|| eProvincia
eInteres }|---|| eServicio
eInteres }|---|| eEntidad
eUsuario ||----|| eConfigNoti
@enduml  
