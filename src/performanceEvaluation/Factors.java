package performanceEvaluation;

import facetRanking.Facet;
import listClustering.Cluster;
import util.Data;

import java.util.HashMap;
import java.util.Map;

public class Factors {

    Data data;
    private Map<Integer,Double> tP,fN,fP,tN;
    


    Factors(Data data){
        this.data = data;
        tN = new HashMap<>();
        tP = new HashMap<>();
        fN = new HashMap<>();
        tP = new HashMap<>();
    }

    public void calculateAll(Cluster cluster){


    }

    public double gettP(int qId) {
        return tP.get(qId);
    }

    public double getfN(int qId) {
        return fN.get(qId);
    }

    public double getfP(int qId) {
        return fP.get(qId);
    }

    public double gettN(int qId) {
        return tN.get(qId);
    }

    public void printAll()
    {
        for(Integer qId:tP.keySet()){
            System.out.println("<-<-<-<-<-::: Query : "+qId+" :::->->->->->");
            System.out.println("\tTrue-Positive : "+tP.get(qId));
            System.out.println("\tFalse-Negitive : "+fN.get(qId));
            System.out.println("\tFalse-Positive : "+fP.get(qId));
            System.out.println("\tTrue-Negitive : "+tN.get(qId));
            System.out.println("-------------------------------------------");
        }
    }


}
