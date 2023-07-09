package rankings;

public class RankingComunidades {

    private float calcularImpacto(Comunidades comunidad){
        List<IncidenteDeComunidad> incidentes = getIncidentes(comunidad);
        return comunidad.cantidadDeMiembros() / incidentes.size();
    }


    public List<Comunidades> generarRanking(List<Comunidades> comunidades) {
        Collections.sort(comunidades, Comparator.comparingFloat((Comunidades comunidad) -> calcularImpacto(comunidad)));
        return comunidades;
    }


}
