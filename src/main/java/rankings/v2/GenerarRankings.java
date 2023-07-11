package rankings.v2;

import java.util.ArrayList;
import java.util.List;

public class GenerarRankings {
    private List<Ranking> rankings;

    public GenerarRankings() {
        this.rankings = new ArrayList<>();
    }

    public void agregarRanking(Ranking ranking){
        rankings.add(ranking);
    }

    public void eliminarRanking(Ranking ranking){
        rankings.remove(ranking);
    }

    public void generarRankings(){
        rankings.forEach(Ranking::generarRanking);
    }
}
