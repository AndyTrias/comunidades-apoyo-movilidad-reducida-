package rankings;

public class RankingMayorCantidad extends RankingEntidades {

    @Override
    protected boolean validarIncidente(Incidente incidente, List<Incidente> incidentesPrestacion) {
        return incidentesPrestacion.stream().noneMatch(incidentePrestacion -> incidentePrestacion.getFechaDeApertura().getTime() - incidente.getFechaDeApertura().getTime() < 24 * 60 * 60 * 1000 && incidentePrestacion.estaAbierto()) ;
    }


    @Override
    public List<Entidad> generarRanking(List<Entidad> entidades) {
        Collections.sort(entidades, Comparator.comparingInt((Entidad entidad) -> obtenerIncidentesDeEntidad(entidad).size()));
        return entidades;
    }




}
