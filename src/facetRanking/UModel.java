package facetRanking;

import listClustering.Cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UModel {

    public static double updateScore(Cluster cluster)
    {
        double sC=0;
        for(String site:cluster.getSiteLists().keySet()) {
            List<Double> weights = new ArrayList<>();
            for (Integer key : cluster.getSiteLists().get(site)) {
                weights.add(cluster.getWeights().get(key));
            }
            sC += Collections.max(weights);
        }
        System.out.println(sC);
        return sC;
    }

}

