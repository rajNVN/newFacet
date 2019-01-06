package performanceEvaluation;

import listClustering.Cluster;
import util.Data;

import java.util.HashMap;
import java.util.Map;

public class Purity {

    Purity(Data data){
        purities= new HashMap<>();
        this.data=data;
    }
    private Map<Integer,Double> purities;
    private Data data;

    public void calculatePuritiy()
    {
        for(Integer qId:data.getQueryClusters().keySet()){
            long maxSum=0, totalSum=0;
            for(Cluster cluster:data.getQueryClusters().get(qId)){
                maxSum+=cluster.getMaxSize(data);
                totalSum+=cluster.getTotalSize(data);
            }
            purities.put(qId,maxSum/(double)totalSum);
            System.out.println("Purity of "+qId+" is "+maxSum/(double)totalSum);
        }
    }

    public Double purityOf(int qId){
        return purities.get(qId);
    }

    public void printAll(){
        for(int qId:purities.keySet()){
            System.out.println("Purity of "+qId+" is "+purityOf(qId));
        }
    }

}
