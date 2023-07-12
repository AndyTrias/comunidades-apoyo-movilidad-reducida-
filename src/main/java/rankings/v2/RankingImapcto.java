//package rankings.v2;
//
//import comunidades.Comunidad;
//import incidentes.Incidente;
//import repositiorios.RepoComunidades;
//
//import java.util.Comparator;
//import java.util.List;
//
//public class RankingImapcto implements RankingComunidades {
//
//    public List<Comunidad> generarRanking() {
//        List<Comunidad> comunidades = RepoComunidades.getInstance().getComunidades();
//        comunidades.sort(Comparator.comparing(this::calcularImpacto));
//        return comunidades;
//    }
//
//    private float calcularImpacto(Comunidad comunidad){
//        List<Incidente> incidentes = comunidad.getTodosLosIncidentes();
//        return (float) comunidad.getCantidadDeUsuarios() / incidentes.size();
//    }
//}
