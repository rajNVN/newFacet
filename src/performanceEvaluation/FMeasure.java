package performanceEvaluation;

import util.Data;

import java.util.HashMap;
import java.util.Map;

public class FMeasure {

    private Data data;
    private Map<Integer,Double> measures;
    private double beeta = 2.0;


    FMeasure(Data data){
        this.data = data;
        measures = new HashMap<>();
    }

    public void calculateFmeasure(Factors factors){
        for (Integer qId:data.getQueryClusters().keySet()) {
            double P, R;
            P = factors.gettP(qId) / (factors.gettP(qId) + factors.getfP(qId));
            R = factors.gettP(qId) / (factors.gettP(qId) + factors.getfN(qId));
            measures.put(qId,((beeta*beeta+1)*P*R)/beeta*beeta*P+R);
        }
    }

    public double getFMeasure(int qId){
        return measures.get(qId);
    }




}
