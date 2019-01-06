package facetRanking;

import listClustering.Cluster;
import util.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Facet {

    private String domain;
    private Integer qId;
    private Double rank;
    private Map<Integer,ArrayList<String>> lists;
    private Map<Integer,Double> weights;
    Facet(String domain,int qId)
    {
        this.domain = domain;
        this.qId = qId;
        lists = new HashMap<>();
        weights = new HashMap<>();
    }

    public void addList(int lId, ArrayList<String> list, double weight) {
        lists.put(lId,list);
        weights.put(lId,weight);
    }

    public void constuct(Cluster cluster,Data data) {
        double max=-1;
        for(Integer key: cluster.getLists()) {
            if (data.getSite(qId).get(key).equals(domain)) {
                lists.put(key, data.getList(qId).get(key));
                weights.put(key, cluster.getWeights().get(key));
                if(cluster.getWeights().get(key)>max)
                    max=cluster.getWeights().get(key);
            }
        }
        rank = max;
    }

    public String getDomain() {
        return domain;
    }

    public Integer getqId() {
        return qId;
    }

    public Double getRank() {
        return rank;
    }

    public static void rankFacet(Data data) {
        for (int qId : data.getQueryClusters().keySet()) {
            for (Cluster cluster:data.getQueryClusters().get(qId)) {
                cluster.parseSiteLists(data);
                cluster.setS(UModel.updateScore(cluster));
            }
            //System.out.println("Highest value : " + Collections.max(list));
        }
    }


}
