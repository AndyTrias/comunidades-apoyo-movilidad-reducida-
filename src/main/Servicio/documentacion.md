# API de Clasificación de Entidades

Este es un servicio web simple que toma un conjunto de entidades y las clasifica en función de un criterio de ranking. El criterio de ranking se calcula utilizando la suma de los tiempos de resolución de incidentes, el número de incidentes no resueltos y la cantidad total de miembros afectados. Puedes utilizar este servicio para ordenar las entidades según su rendimiento en la gestión de incidentes.

## Uso

### Endpoint

- `POST /sort`

### Parámetros de entrada

- El servicio espera recibir un objeto JSON con una lista de entidades y sus incidentes asociados.

Ejemplo de entrada:

```json
{
   "entidades": [
        {
            "entidad": "Entidad A",
            "incidentes": [
                {
                    "fechaApertura": "2023-09-01",
                    "fechaCierre": "2023-09-16",
                    "miembrosAfectados": 47
                },
                {
                    "fechaApertura": "2023-08-15",
                    "fechaCierre": "2023-08-25",
                    "miembrosAfectados": 32
                },
                {
                    "fechaApertura": "2023-07-10",
                    "fechaCierre": "2023-07-21",
                    "miembrosAfectados": 22
                }
            ]
        },
        {
            "entidad": "Entidad B",
            "incidentes": [
                {
                    "fechaApertura": "2023-09-03",
                    "fechaCierre": "2023-09-09",
                    "miembrosAfectados": 12
                },
                {
                    "fechaApertura": "2023-08-18",
                    "fechaCierre": "2023-08-23",
                    "miembrosAfectados": 8
                }
            ]
        },
        ...
   ]
}
```

### Respuesta

- El servicio clasificará las entidades en función de su rendimiento y devolverá una lista de entidades ordenadas en JSON.

Ejemplo de respuesta:

```json
[
    "Entidad B",
    "Entidad A",
    ...
]
```

### Cálculo del Criterio de Ranking

El criterio de ranking se calcula de la siguiente manera:

```
Criterio de Ranking = Suma de Tiempos de Resolución + (Coeficiente de No Resueltos) * (Cantidad de Incidentes No Resueltos) * (Cantidad Total de Miembros Afectados)
```

Donde:
- `Suma de Tiempos de Resolución` es la suma de los días que tomó resolver los incidentes de una entidad.
- `Coeficiente de No Resueltos` es un valor fijo configurado en el servidor (actualmente establecido en 4.5).
- `Cantidad de Incidentes No Resueltos` es el número de incidentes que no se han resuelto (incidentes con la fecha de cierre vacía).
- `Cantidad Total de Miembros Afectados` es la suma de la cantidad de miembros afectados en todos los incidentes de una entidad.


