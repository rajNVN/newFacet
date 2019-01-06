package performanceEvaluation;

import facetRanking.Facet;
import util.Data;

import java.util.Map;

public class Performance {

    private Purity purity;
    private Map<Facet,FMeasure> fMeasure;
    private RandomIndex randomIndex;
    private Data data;
    private Factors factors;

    public Performance(Data data){
        this.data = data;
        purity = new Purity(data);
        factors = new Factors(data);
        fMeasure = new FMeasure(data);
        randomIndex = new RandomIndex(data);
    }


}
