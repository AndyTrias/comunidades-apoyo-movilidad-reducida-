from datetime import datetime

from flask import Flask, request, jsonify

# Coeficiente de no resueltos
CNF = 4.5


def tiempoResolucion(incidente):
    fechaAperura = datetime.strptime(incidente['fechaApertura'], '%Y-%m-%d')
    fechaCierre = datetime.strptime(incidente['fechaCierre'], '%Y-%m-%d')

    tiempoResolucion = int((fechaCierre - fechaAperura).days)

    return tiempoResolucion


def sumTiemposResolucion(entidad):
    return sum([tiempoResolucion(incidente) for incidente in entidad['incidentes']])


def cantIncidentesNoResueltos(entidad):
    return len(list(filter(lambda incidente: incidente['fechaCierre'] == [], entidad['incidentes'])))


def cantidadMiembros(entidad):
    return sum(int(incidente['miembrosAfectados']) for incidente in entidad['incidentes'])


def criterioRanking(entidad):
    return sumTiemposResolucion(entidad) + CNF * cantIncidentesNoResueltos(entidad) * cantidadMiembros(entidad)


app = Flask(__name__)


@app.route('/sort', methods=['POST'])
def sort_json():


    data = request.get_json()

    if not data or not isinstance(data['entidades'], list):
        return jsonify({'error': 'Invalid JSON data'}), 400

    entidades = data['entidades']

    sorted_data = sorted(entidades, key=criterioRanking)

    sorted_data = [entidad["entidad"] for entidad in sorted_data]

    return jsonify(sorted_data), 200

    '''
    except Exception as e:
        return jsonify({'error': str(e)}), 500
    '''

if __name__ == '__main__':
    app.run()
