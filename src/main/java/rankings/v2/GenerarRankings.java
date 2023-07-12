//package rankings.v2;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GenerarRankings {
//    private List<RankingEntidades> rankings;
//    private List<RankingComunidades> rankingsComunidades;
//
//    public GenerarRankings() {
//        this.rankings = new ArrayList<>();
//    }
//
//    public void agregarRankingEntidades(RankingEntidades ranking){
//        rankings.add(ranking);
//    }
//
//    public void agregarRankingComunidades(RankingComunidades ranking){
//        rankingsComunidades.add(ranking);
//    }
//
//    public void generarRankings(){
//        rankings.forEach(RankingEntidades::generarRanking);
//        rankingsComunidades.forEach(RankingComunidades::generarRanking);
//    }
//}
