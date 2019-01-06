package performanceEvaluation;

import facetRanking.Facet;
import util.Data;

import java.util.HashMap;
import java.util.Map;

public class RandomIndex {

    private Data data;
    private Map<Facet,Double> RI;

    RandomIndex(Data data){
        this.data = data;
        RI = new HashMap<>();
    }
    public void calculateRI(Factors factors){
        for (Facet facet:data.getQueryClusters().keySet()) {
            double res = (factors.gettP(qId)+factors.gettN(qId)) / ((factors.gettP(qId) + factors.getfP(qId)) + (factors.gettN(qId) + factors.getfN(qId)));
            RI.put(facet,res);
        }
    }

}
