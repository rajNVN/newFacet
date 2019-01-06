package listClustering;

import util.Data;
import util.Sorter;

import java.util.*;

public class Clusterer {

    public static double diaMax=0.6, wC=2.0;


    public static void formClusters(Data data) {
        System.out.println("FORM CLUSTERS :->");
        for (int qId : data.getQueryLists().keySet()) {
            System.out.println("QId - "+qId);
            boolean created;
            List<Cluster> clusters = new ArrayList<>();
            Map<Integer, Double> remainingLists = Sorter.sortByValues(data.getQuerySl().get(qId));
            while (!remainingLists.isEmpty()) {
                created=false;
                System.out.println("remaining count : "+remainingLists.size());
                Cluster cluster=null;
                List<Integer> rem=new ArrayList<>();
                for (Iterator<Integer> key = remainingLists.keySet().iterator(); key.hasNext(); ) {
                    int thisKey = key.next();
                    //System.out.println(qId+"|"+thisKey);
                    if (!created) {
                        cluster=new Cluster(qId, thisKey);
                        cluster.addList(qId + "|" + thisKey, data);
                        created = true;
                        System.out.println("cluster initialize ");
                        continue;
                    }
                    Double d1 = Distance.getDistance(data.getList(qId).get(cluster.getCenterKey()), data.getList(qId).get(thisKey));
                    //Double d2 = Distance.getDistanceByNewMethod(data.getList(qId).get(cluster.getCenterKey()), data.getList(qId).get(thisKey));
                    //System.out.println("DL : "+dl);
                    //System.out.println("distance between list:"+ cluster.getCenterKey() + " -> list:" + thisKey +" is " + d1 + "(old)," + d2 + "(new)");

                    if (d1 <= diaMax) {
                        cluster.addList(qId + "|" + thisKey, data);
                        System.out.println("addList|"+remainingLists.get(thisKey));
                        rem.add(thisKey);
                    }
                }
                rem.add(cluster.getCenterKey());
                for(int remKey:rem)
                    remainingLists.remove(remKey);
                if (cluster.getSitesCount(data) >= wC)
                    clusters.add(cluster);
            }
            //write fromSites related code here...
            data.getQueryClusters().put(qId,clusters);
        }
    }
}
