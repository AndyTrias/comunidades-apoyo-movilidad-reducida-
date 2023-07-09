package rankings;

public class RankingMayorTiempo extends RankingEntidades {

    private float calcularPromedio(Entidad entidad){
        List<Incidente> incidentes = obtenerIncidentesDeEntidad(entidad);
        float suma = 0;
        for (Incidente incidente : incidentes) {
            suma += incidente.tiempoActivo();
        }
        return suma / incidentes.size();
    }

    @Override
    public List<Entidad> generarRanking(List<Entidad> entidades) {
        Collections.sort(entidades, Comparator.comparingFloat((Entidad entidad) -> calcularPromedio(entidad)));
        return entidades;
    }

}
